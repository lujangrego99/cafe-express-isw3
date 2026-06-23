package ar.edu.uch.tpfinal.cafe.pedido.estado;

import java.util.List;

/**
 * Patron STATE - ConcreteState (terminal).
 *
 * El pedido fue retirado por el cliente. No admite mas transiciones: hereda de
 * {@link EstadoBase} el comportamiento "no permitido" para todas las acciones.
 */
public class EntregadoState extends EstadoBase {

    @Override
    public String getNombre() {
        return "ENTREGADO";
    }

    @Override
    public List<String> accionesPermitidas() {
        return List.of();
    }
}
