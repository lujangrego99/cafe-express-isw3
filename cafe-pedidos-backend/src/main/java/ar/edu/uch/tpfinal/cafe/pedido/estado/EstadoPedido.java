package ar.edu.uch.tpfinal.cafe.pedido.estado;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;

import java.util.List;

/**
 * Patron STATE - rol State.
 *
 * Cada estado del pedido implementa esta interfaz y define como reacciona el
 * pedido ante cada operacion. El {@link Pedido} (Context) delega en su estado
 * actual, de modo que no hay un switch gigante por operacion: agregar un estado
 * nuevo es crear una clase nueva (principio Open/Closed).
 */
public interface EstadoPedido {

    /** Nombre del estado, usado en la API y la UI. */
    String getNombre();

    /** Pasa el pedido a preparacion (cocina lo toma). */
    void comenzarPreparacion(Pedido pedido);

    /** Marca el pedido como listo para entregar. */
    void marcarListo(Pedido pedido);

    /** Entrega el pedido al cliente (estado terminal). */
    void entregar(Pedido pedido);

    /** Cancela el pedido (estado terminal). */
    void cancelar(Pedido pedido);

    /**
     * Acciones permitidas en este estado. El backend se lo informa al frontend
     * para que habilite/deshabilite botones (prevencion de errores de UX) en vez
     * de dejar que el usuario intente algo invalido.
     */
    List<String> accionesPermitidas();
}
