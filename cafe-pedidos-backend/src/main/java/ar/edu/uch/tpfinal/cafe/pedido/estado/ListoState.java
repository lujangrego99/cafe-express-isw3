package ar.edu.uch.tpfinal.cafe.pedido.estado;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;

import java.util.List;

/**
 * Patron STATE - ConcreteState.
 *
 * El pedido esta listo en la barra esperando ser retirado. Solo se puede
 * entregar; ya no se cancela (el cafe ya fue preparado).
 */
public class ListoState extends EstadoBase {

    @Override
    public String getNombre() {
        return "LISTO";
    }

    @Override
    public void entregar(Pedido pedido) {
        pedido.cambiarEstado(Estados.ENTREGADO);
    }

    @Override
    public List<String> accionesPermitidas() {
        return List.of("entregar");
    }
}
