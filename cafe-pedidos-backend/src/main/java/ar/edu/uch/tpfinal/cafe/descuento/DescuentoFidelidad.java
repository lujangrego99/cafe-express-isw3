package ar.edu.uch.tpfinal.cafe.descuento;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;

/**
 * Patron STRATEGY - ConcreteStrategy: canje de puntos de fidelidad. Cada punto
 * equivale a $50, sin pasarse del subtotal del pedido.
 */
public class DescuentoFidelidad implements EstrategiaDescuento {

    private static final double VALOR_PUNTO = 50;

    @Override
    public String getNombre() {
        return "Canje de puntos";
    }

    @Override
    public double calcularDescuento(double subtotal, Pedido pedido) {
        double canje = pedido.getPuntosFidelidad() * VALOR_PUNTO;
        return Math.min(canje, subtotal);
    }
}
