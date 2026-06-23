package ar.edu.uch.tpfinal.cafe.pedido.estado;

import java.util.List;

/**
 * Patron STATE - ConcreteState (terminal).
 *
 * El pedido fue cancelado. Estado final sin transiciones posibles.
 */
public class CanceladoState extends EstadoBase {

    @Override
    public String getNombre() {
        return "CANCELADO";
    }

    @Override
    public List<String> accionesPermitidas() {
        return List.of();
    }
}
