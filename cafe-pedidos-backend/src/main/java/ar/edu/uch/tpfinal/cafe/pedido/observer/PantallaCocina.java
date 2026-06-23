package ar.edu.uch.tpfinal.cafe.pedido.observer;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Patron OBSERVER - ConcreteObserver.
 *
 * Pantalla de la cocina (Kitchen Display System). Mantiene la lista de comandas
 * "vivas" que el barista debe atender. Cuando un pedido se entrega o cancela, lo
 * saca del tablero. El frontend de cocina consulta este observador por polling.
 */
@Component
public class PantallaCocina implements ObservadorPedido {

    /** id de pedido -> resumen visible en el tablero. */
    private final Map<Long, Comanda> comandas = new ConcurrentHashMap<>();

    @Override
    public void actualizar(Pedido pedido) {
        String estado = pedido.getEstado().getNombre();
        if (estado.equals("ENTREGADO") || estado.equals("CANCELADO")) {
            comandas.remove(pedido.getId());
        } else {
            comandas.put(pedido.getId(),
                    new Comanda(pedido.getId(), pedido.getCliente(), estado, pedido.descripcionItems()));
        }
    }

    /** Comandas activas ordenadas por numero de pedido (las mas viejas primero). */
    public List<Comanda> getComandasActivas() {
        List<Comanda> activas = new ArrayList<>(comandas.values());
        activas.sort((a, b) -> Long.compare(a.id(), b.id()));
        return activas;
    }

    /** Vista de una comanda en el tablero de cocina. */
    public record Comanda(Long id, String cliente, String estado, List<String> items) {
    }
}
