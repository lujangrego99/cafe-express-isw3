package ar.edu.uch.tpfinal.cafe.pedido.estado;

/**
 * Registro de las instancias unicas (singletons) de cada estado.
 *
 * Los ConcreteState no guardan datos del pedido (lo reciben por parametro), por
 * lo que pueden compartirse entre todos los pedidos. Esto evita crear objetos de
 * estado nuevos en cada transicion.
 */
public final class Estados {

    public static final EstadoPedido PENDIENTE = new PendienteState();
    public static final EstadoPedido EN_PREPARACION = new EnPreparacionState();
    public static final EstadoPedido LISTO = new ListoState();
    public static final EstadoPedido ENTREGADO = new EntregadoState();
    public static final EstadoPedido CANCELADO = new CanceladoState();

    private Estados() {
    }
}
