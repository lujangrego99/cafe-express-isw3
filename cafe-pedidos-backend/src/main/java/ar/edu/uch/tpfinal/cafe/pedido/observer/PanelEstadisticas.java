package ar.edu.uch.tpfinal.cafe.pedido.observer;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Patron OBSERVER - ConcreteObserver.
 *
 * Acumula metricas operativas a medida que los pedidos cambian de estado:
 * cuantos se entregaron, cuantos se cancelaron y la facturacion de lo entregado.
 * Es un tercer observador que reacciona al MISMO evento sin que el Subject sepa
 * de su existencia.
 */
@Component
public class PanelEstadisticas implements ObservadorPedido {

    private final Map<String, AtomicLong> conteoPorEstado = new ConcurrentHashMap<>();
    private double facturacionEntregada = 0;

    @Override
    public void actualizar(Pedido pedido) {
        String estado = pedido.getEstado().getNombre();
        conteoPorEstado.computeIfAbsent(estado, e -> new AtomicLong()).incrementAndGet();
        if (estado.equals("ENTREGADO")) {
            synchronized (this) {
                facturacionEntregada += pedido.getTotal();
            }
        }
    }

    public Map<String, Long> getConteoPorEstado() {
        Map<String, Long> snapshot = new ConcurrentHashMap<>();
        conteoPorEstado.forEach((k, v) -> snapshot.put(k, v.get()));
        return snapshot;
    }

    public synchronized double getFacturacionEntregada() {
        return facturacionEntregada;
    }
}
