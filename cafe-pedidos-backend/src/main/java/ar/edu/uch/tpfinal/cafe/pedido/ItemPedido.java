package ar.edu.uch.tpfinal.cafe.pedido;

import ar.edu.uch.tpfinal.cafe.bebida.Bebida;

/**
 * Una linea del pedido: una {@link Bebida} (posiblemente decorada con varios
 * adicionales) y la cantidad pedida.
 */
public class ItemPedido {

    private final Bebida bebida;
    private final int cantidad;

    public ItemPedido(Bebida bebida, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        this.bebida = bebida;
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return bebida.getDescripcion();
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return bebida.getCosto();
    }

    public double getSubtotal() {
        return bebida.getCosto() * cantidad;
    }
}
