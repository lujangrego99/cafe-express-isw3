package ar.edu.uch.tpfinal.cafe.pedido;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Almacen en memoria de pedidos. Para el alcance del TP (complejidad media) no se
 * usa base de datos: los pedidos viven mientras corre la aplicacion.
 */
@Repository
public class PedidoRepository {

    private final Map<Long, Pedido> pedidos = new ConcurrentHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public Long siguienteId() {
        return secuencia.incrementAndGet();
    }

    public Pedido guardar(Pedido pedido) {
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    public List<Pedido> listar() {
        List<Pedido> lista = new ArrayList<>(pedidos.values());
        lista.sort((a, b) -> Long.compare(b.getId(), a.getId()));
        return lista;
    }
}
