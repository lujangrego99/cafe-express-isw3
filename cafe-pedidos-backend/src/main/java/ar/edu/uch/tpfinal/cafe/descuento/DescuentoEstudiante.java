package ar.edu.uch.tpfinal.cafe.descuento;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;

/**
 * Patron STRATEGY - ConcreteStrategy: 10% de descuento con credencial de
 * estudiante.
 */
public class DescuentoEstudiante implements EstrategiaDescuento {

    @Override
    public String getNombre() {
        return "Estudiante (10%)";
    }

    @Override
    public double calcularDescuento(double subtotal, Pedido pedido) {
        return subtotal * 0.10;
    }
}
