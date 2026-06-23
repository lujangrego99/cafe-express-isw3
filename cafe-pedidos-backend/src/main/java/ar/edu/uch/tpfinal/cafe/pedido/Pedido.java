package ar.edu.uch.tpfinal.cafe.pedido;

import ar.edu.uch.tpfinal.cafe.pedido.estado.EstadoPedido;
import ar.edu.uch.tpfinal.cafe.pedido.estado.Estados;
import ar.edu.uch.tpfinal.cafe.pedido.observer.ObservadorPedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad central del dominio. Cumple tres roles de patron a la vez:
 *
 * <ul>
 *   <li>STATE - Context: delega las operaciones de ciclo de vida
 *       (preparar, listo, entregar, cancelar) en su {@link EstadoPedido} actual.</li>
 *   <li>OBSERVER - Subject: mantiene la lista de {@link ObservadorPedido} y los
 *       notifica en cada cambio de estado.</li>
 *   <li>Receptor del resultado de STRATEGY: guarda el nombre de la politica de
 *       descuento aplicada y el monto resultante.</li>
 * </ul>
 */
public class Pedido {

    private final Long id;
    private final String cliente;
    private final List<ItemPedido> items;
    private final LocalDateTime creadoEn;
    private final int puntosFidelidad;

    // STATE: estado actual del pedido (arranca PENDIENTE).
    private EstadoPedido estado = Estados.PENDIENTE;

    // OBSERVER: interesados en los cambios de este pedido.
    private final List<ObservadorPedido> observadores = new ArrayList<>();

    // STRATEGY: resultado del calculo de descuento.
    private String nombreDescuento = "Sin descuento";
    private double montoDescuento = 0;

    // Historial de estados para trazabilidad / UI.
    private final List<RegistroEstado> historial = new ArrayList<>();

    public Pedido(Long id, String cliente, List<ItemPedido> items, int puntosFidelidad, LocalDateTime creadoEn) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe tener al menos un item");
        }
        this.id = id;
        this.cliente = cliente;
        this.items = items;
        this.puntosFidelidad = puntosFidelidad;
        this.creadoEn = creadoEn;
        this.historial.add(new RegistroEstado(estado.getNombre(), creadoEn));
    }

    // --- OBSERVER (Subject) ---

    public void agregarObservador(ObservadorPedido observador) {
        observadores.add(observador);
    }

    private void notificarObservadores() {
        for (ObservadorPedido o : observadores) {
            o.actualizar(this);
        }
    }

    // --- STATE (Context) ---

    /**
     * Cambio de estado controlado por los ConcreteState. Registra el historial y
     * dispara la notificacion a los observadores: aca se entrelazan State y Observer.
     */
    public void cambiarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
        this.historial.add(new RegistroEstado(nuevoEstado.getNombre(), LocalDateTime.now()));
        notificarObservadores();
    }

    public void comenzarPreparacion() {
        estado.comenzarPreparacion(this);
    }

    public void marcarListo() {
        estado.marcarListo(this);
    }

    public void entregar() {
        estado.entregar(this);
    }

    public void cancelar() {
        estado.cancelar(this);
    }

    // --- STRATEGY (resultado) ---

    public void aplicarDescuento(String nombreDescuento, double montoDescuento) {
        this.nombreDescuento = nombreDescuento;
        this.montoDescuento = montoDescuento;
    }

    // --- Calculos ---

    public double getSubtotal() {
        return items.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }

    public double getTotal() {
        return getSubtotal() - montoDescuento;
    }

    public List<String> descripcionItems() {
        List<String> desc = new ArrayList<>();
        for (ItemPedido item : items) {
            desc.add(item.getCantidad() + "x " + item.getDescripcion());
        }
        return desc;
    }

    // --- Getters ---

    public Long getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public String getNombreDescuento() {
        return nombreDescuento;
    }

    public double getMontoDescuento() {
        return montoDescuento;
    }

    public List<RegistroEstado> getHistorial() {
        return historial;
    }

    /** Una entrada del historial de estados del pedido. */
    public record RegistroEstado(String estado, LocalDateTime momento) {
    }
}
