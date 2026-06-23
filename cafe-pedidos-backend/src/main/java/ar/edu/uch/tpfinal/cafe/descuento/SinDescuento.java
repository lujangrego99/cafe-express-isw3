package ar.edu.uch.tpfinal.cafe.descuento;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;

/**
 * Patron STRATEGY - ConcreteStrategy por defecto (Null Object): no aplica
 * descuento. Evita tener que chequear null cuando el pedido no tiene promo.
 */
public class SinDescuento implements EstrategiaDescuento {

    @Override
    public String getNombre() {
        return "Sin descuento";
    }

    @Override
    public double calcularDescuento(double subtotal, Pedido pedido) {
        return 0;
    }
}
