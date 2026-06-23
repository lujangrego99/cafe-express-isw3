package ar.edu.uch.tpfinal.cafe.pedido.estado;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;

import java.util.List;

/**
 * Patron STATE - ConcreteState.
 *
 * Estado inicial: el pedido fue confirmado por el cliente y espera que la cocina
 * lo tome. Desde aca se puede pasar a preparacion o cancelar.
 */
public class PendienteState extends EstadoBase {

    @Override
    public String getNombre() {
        return "PENDIENTE";
    }

    @Override
    public void comenzarPreparacion(Pedido pedido) {
        pedido.cambiarEstado(Estados.EN_PREPARACION);
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.cambiarEstado(Estados.CANCELADO);
    }

    @Override
    public List<String> accionesPermitidas() {
        return List.of("preparar", "cancelar");
    }
}
