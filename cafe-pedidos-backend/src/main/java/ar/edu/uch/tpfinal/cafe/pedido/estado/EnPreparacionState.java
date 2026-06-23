package ar.edu.uch.tpfinal.cafe.pedido.estado;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;

import java.util.List;

/**
 * Patron STATE - ConcreteState.
 *
 * El barista esta preparando el pedido. Puede marcarse como listo o cancelarse
 * (por ejemplo, si falta un insumo).
 */
public class EnPreparacionState extends EstadoBase {

    @Override
    public String getNombre() {
        return "EN_PREPARACION";
    }

    @Override
    public void marcarListo(Pedido pedido) {
        pedido.cambiarEstado(Estados.LISTO);
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.cambiarEstado(Estados.CANCELADO);
    }

    @Override
    public List<String> accionesPermitidas() {
        return List.of("listo", "cancelar");
    }
}
