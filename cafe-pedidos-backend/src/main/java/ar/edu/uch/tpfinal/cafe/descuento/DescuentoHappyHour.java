package ar.edu.uch.tpfinal.cafe.descuento;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;

/**
 * Patron STRATEGY - ConcreteStrategy: 20% en la franja de "happy hour"
 * (15:00 a 18:00). Fuera de ese horario no aplica. La regla de cuando corresponde
 * queda encapsulada dentro de la estrategia.
 */
public class DescuentoHappyHour implements EstrategiaDescuento {

    @Override
    public String getNombre() {
        return "Happy Hour (20%)";
    }

    @Override
    public double calcularDescuento(double subtotal, Pedido pedido) {
        int hora = pedido.getCreadoEn().getHour();
        boolean enFranja = hora >= 15 && hora < 18;
        return enFranja ? subtotal * 0.20 : 0;
    }
}
