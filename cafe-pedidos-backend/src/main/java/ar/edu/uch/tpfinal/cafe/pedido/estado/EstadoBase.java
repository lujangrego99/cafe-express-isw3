package ar.edu.uch.tpfinal.cafe.pedido.estado;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;

import java.util.List;

/**
 * Patron STATE - base abstracta de los ConcreteState.
 *
 * Implementa todas las operaciones como "no permitido". Cada estado concreto
 * solo sobreescribe las transiciones que SI son validas para el. Asi se evita
 * repetir el manejo de transiciones invalidas en cada estado.
 */
public abstract class EstadoBase implements EstadoPedido {

    @Override
    public void comenzarPreparacion(Pedido pedido) {
        noPermitido("comenzar la preparacion");
    }

    @Override
    public void marcarListo(Pedido pedido) {
        noPermitido("marcar como listo");
    }

    @Override
    public void entregar(Pedido pedido) {
        noPermitido("entregar");
    }

    @Override
    public void cancelar(Pedido pedido) {
        noPermitido("cancelar");
    }

    @Override
    public List<String> accionesPermitidas() {
        return List.of();
    }

    protected void noPermitido(String accion) {
        throw new TransicionInvalidaException(
                "No se puede " + accion + " un pedido en estado " + getNombre());
    }
}
