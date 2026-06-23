package ar.edu.uch.tpfinal.cafe;

import ar.edu.uch.tpfinal.cafe.bebida.Bebida;
import ar.edu.uch.tpfinal.cafe.bebida.BebidaFactory;
import ar.edu.uch.tpfinal.cafe.descuento.DescuentoEstudiante;
import ar.edu.uch.tpfinal.cafe.descuento.EstrategiaDescuento;
import ar.edu.uch.tpfinal.cafe.pedido.ItemPedido;
import ar.edu.uch.tpfinal.cafe.pedido.Pedido;
import ar.edu.uch.tpfinal.cafe.pedido.estado.TransicionInvalidaException;
import ar.edu.uch.tpfinal.cafe.pedido.observer.PantallaCocina;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Pruebas unitarias de los cuatro patrones GOF integrados. */
class PatronesTest {

    @Test
    void decorator_acumulaCostoYDescripcion() {
        // Latte (1800) + Shot (500) + Vainilla (350) = 2650
        Bebida bebida = BebidaFactory.construir("lat", List.of("shot", "vainilla"));
        assertEquals(2650, bebida.getCosto());
        assertEquals("Latte + Shot extra + Jarabe de vainilla", bebida.getDescripcion());
    }

    @Test
    void strategy_descuentoEstudianteEsDiezPorciento() {
        Pedido pedido = pedidoDe(new ItemPedido(BebidaFactory.construir("lat", List.of()), 2)); // 3600
        EstrategiaDescuento estrategia = new DescuentoEstudiante();
        assertEquals(360, estrategia.calcularDescuento(pedido.getSubtotal(), pedido));
    }

    @Test
    void state_flujoFelizYTransicionInvalida() {
        Pedido pedido = pedidoDe(new ItemPedido(BebidaFactory.construir("esp", List.of()), 1));
        assertEquals("PENDIENTE", pedido.getEstado().getNombre());

        // Entregar sin preparar es invalido.
        assertThrows(TransicionInvalidaException.class, pedido::entregar);

        pedido.comenzarPreparacion();
        assertEquals("EN_PREPARACION", pedido.getEstado().getNombre());
        pedido.marcarListo();
        assertEquals("LISTO", pedido.getEstado().getNombre());
        pedido.entregar();
        assertEquals("ENTREGADO", pedido.getEstado().getNombre());
    }

    @Test
    void observer_pantallaCocinaSeActualizaYLimpia() {
        PantallaCocina cocina = new PantallaCocina();
        Pedido pedido = pedidoDe(new ItemPedido(BebidaFactory.construir("ame", List.of()), 1));
        pedido.agregarObservador(cocina);

        pedido.comenzarPreparacion(); // notifica -> aparece en cocina
        assertEquals(1, cocina.getComandasActivas().size());

        pedido.marcarListo();
        pedido.entregar(); // al entregar sale del tablero
        assertTrue(cocina.getComandasActivas().isEmpty());
    }

    private Pedido pedidoDe(ItemPedido item) {
        return new Pedido(1L, "Test", List.of(item), 0, LocalDateTime.now());
    }
}
