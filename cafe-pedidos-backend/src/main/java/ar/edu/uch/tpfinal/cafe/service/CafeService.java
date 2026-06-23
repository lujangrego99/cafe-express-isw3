package ar.edu.uch.tpfinal.cafe.service;

import ar.edu.uch.tpfinal.cafe.bebida.Bebida;
import ar.edu.uch.tpfinal.cafe.bebida.BebidaFactory;
import ar.edu.uch.tpfinal.cafe.descuento.EstrategiaDescuento;
import ar.edu.uch.tpfinal.cafe.descuento.EstrategiaDescuentoFactory;
import ar.edu.uch.tpfinal.cafe.pedido.ItemPedido;
import ar.edu.uch.tpfinal.cafe.pedido.Pedido;
import ar.edu.uch.tpfinal.cafe.pedido.PedidoRepository;
import ar.edu.uch.tpfinal.cafe.pedido.observer.NotificadorCliente;
import ar.edu.uch.tpfinal.cafe.pedido.observer.PanelEstadisticas;
import ar.edu.uch.tpfinal.cafe.pedido.observer.PantallaCocina;
import ar.edu.uch.tpfinal.cafe.web.dto.CrearPedidoRequest;
import ar.edu.uch.tpfinal.cafe.web.dto.PedidoResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Servicio de aplicacion: orquesta los cuatro patrones.
 *
 * <ol>
 *   <li>DECORATOR: arma cada bebida con sus adicionales via {@code BebidaFactory}.</li>
 *   <li>STRATEGY: calcula el descuento segun la politica elegida.</li>
 *   <li>OBSERVER: registra los tres observadores compartidos en cada pedido.</li>
 *   <li>STATE: dispara las transiciones de ciclo de vida del pedido.</li>
 * </ol>
 */
@Service
public class CafeService {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final PedidoRepository repository;
    private final PantallaCocina pantallaCocina;
    private final NotificadorCliente notificadorCliente;
    private final PanelEstadisticas panelEstadisticas;

    public CafeService(PedidoRepository repository,
                       PantallaCocina pantallaCocina,
                       NotificadorCliente notificadorCliente,
                       PanelEstadisticas panelEstadisticas) {
        this.repository = repository;
        this.pantallaCocina = pantallaCocina;
        this.notificadorCliente = notificadorCliente;
        this.panelEstadisticas = panelEstadisticas;
    }

    /** Crea un pedido aplicando Decorator (bebidas) y Strategy (descuento). */
    public PedidoResponse crearPedido(CrearPedidoRequest request) {
        if (request.items() == null || request.items().isEmpty()) {
            throw new IllegalArgumentException("El pedido no tiene items");
        }

        // DECORATOR: cada item se construye como una pila de adicionales sobre la base.
        List<ItemPedido> items = new ArrayList<>();
        for (CrearPedidoRequest.ItemRequest it : request.items()) {
            Bebida bebida = BebidaFactory.construir(it.baseId(), it.adicionales());
            items.add(new ItemPedido(bebida, it.cantidad()));
        }

        String cliente = (request.cliente() == null || request.cliente().isBlank())
                ? "Cliente" : request.cliente().trim();

        Pedido pedido = new Pedido(
                repository.siguienteId(), cliente, items, request.puntosFidelidad(), LocalDateTime.now());

        // OBSERVER: los tres observadores compartidos escuchan a este pedido.
        pedido.agregarObservador(pantallaCocina);
        pedido.agregarObservador(notificadorCliente);
        pedido.agregarObservador(panelEstadisticas);

        // STRATEGY: la politica elegida calcula el descuento sobre el subtotal.
        EstrategiaDescuento estrategia = EstrategiaDescuentoFactory.desde(request.tipoDescuento());
        double descuento = estrategia.calcularDescuento(pedido.getSubtotal(), pedido);
        pedido.aplicarDescuento(estrategia.getNombre(), descuento);

        repository.guardar(pedido);

        // El pedido nace PENDIENTE: avisamos a los observadores de su alta.
        pantallaCocina.actualizar(pedido);
        notificadorCliente.actualizar(pedido);
        panelEstadisticas.actualizar(pedido);

        return aResponse(pedido);
    }

    public List<PedidoResponse> listarPedidos() {
        return repository.listar().stream().map(this::aResponse).toList();
    }

    public PedidoResponse obtenerPedido(Long id) {
        return aResponse(buscar(id));
    }

    // --- STATE: transiciones de ciclo de vida ---

    public PedidoResponse prepararPedido(Long id) {
        Pedido p = buscar(id);
        p.comenzarPreparacion();
        return aResponse(p);
    }

    public PedidoResponse marcarListo(Long id) {
        Pedido p = buscar(id);
        p.marcarListo();
        return aResponse(p);
    }

    public PedidoResponse entregarPedido(Long id) {
        Pedido p = buscar(id);
        p.entregar();
        return aResponse(p);
    }

    public PedidoResponse cancelarPedido(Long id) {
        Pedido p = buscar(id);
        p.cancelar();
        return aResponse(p);
    }

    private Pedido buscar(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("No existe el pedido #" + id));
    }

    // --- Mapeo a DTO ---

    private PedidoResponse aResponse(Pedido p) {
        List<PedidoResponse.ItemResponse> items = p.getItems().stream()
                .map(i -> new PedidoResponse.ItemResponse(
                        i.getDescripcion(), i.getCantidad(), i.getPrecioUnitario(), i.getSubtotal()))
                .toList();

        List<PedidoResponse.HistorialResponse> historial = p.getHistorial().stream()
                .map(h -> new PedidoResponse.HistorialResponse(h.estado(), h.momento().format(FMT)))
                .toList();

        return new PedidoResponse(
                p.getId(),
                p.getCliente(),
                p.getEstado().getNombre(),
                items,
                p.getSubtotal(),
                p.getNombreDescuento(),
                p.getMontoDescuento(),
                p.getTotal(),
                p.getCreadoEn().format(FMT),
                p.getEstado().accionesPermitidas(),
                historial);
    }
}
