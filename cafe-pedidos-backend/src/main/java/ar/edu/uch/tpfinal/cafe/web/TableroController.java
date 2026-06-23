package ar.edu.uch.tpfinal.cafe.web;

import ar.edu.uch.tpfinal.cafe.pedido.observer.NotificadorCliente;
import ar.edu.uch.tpfinal.cafe.pedido.observer.PanelEstadisticas;
import ar.edu.uch.tpfinal.cafe.pedido.observer.PantallaCocina;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Expone el estado de los tres observadores (patron Observer). El frontend de
 * cocina y el panel de estadisticas consultan estos endpoints por polling.
 */
@RestController
@RequestMapping("/api")
public class TableroController {

    private final PantallaCocina pantallaCocina;
    private final NotificadorCliente notificadorCliente;
    private final PanelEstadisticas panelEstadisticas;

    public TableroController(PantallaCocina pantallaCocina,
                            NotificadorCliente notificadorCliente,
                            PanelEstadisticas panelEstadisticas) {
        this.pantallaCocina = pantallaCocina;
        this.notificadorCliente = notificadorCliente;
        this.panelEstadisticas = panelEstadisticas;
    }

    /** Comandas activas en la pantalla de cocina. */
    @GetMapping("/cocina")
    public List<PantallaCocina.Comanda> cocina() {
        return pantallaCocina.getComandasActivas();
    }

    /** Ultimas notificaciones generadas para clientes. */
    @GetMapping("/notificaciones")
    public List<String> notificaciones() {
        return notificadorCliente.getMensajes();
    }

    /** Metricas operativas acumuladas. */
    @GetMapping("/estadisticas")
    public Map<String, Object> estadisticas() {
        return Map.of(
                "conteoPorEstado", panelEstadisticas.getConteoPorEstado(),
                "facturacionEntregada", panelEstadisticas.getFacturacionEntregada());
    }
}
