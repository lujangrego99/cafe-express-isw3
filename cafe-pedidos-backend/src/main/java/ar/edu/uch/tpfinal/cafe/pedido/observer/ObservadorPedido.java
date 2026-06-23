package ar.edu.uch.tpfinal.cafe.pedido.observer;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;

/**
 * Patron OBSERVER - rol Observer.
 *
 * Cualquier interesado en los cambios de estado de un pedido implementa este
 * contrato. El {@link Pedido} (Subject) lo notifica sin conocer su clase concreta.
 */
public interface ObservadorPedido {

    /** El pedido informa que cambio (de estado). */
    void actualizar(Pedido pedido);
}
