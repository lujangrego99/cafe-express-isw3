package ar.edu.uch.tpfinal.cafe.descuento;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;

/**
 * Patron STRATEGY - rol Strategy.
 *
 * Encapsula la forma de calcular el descuento de un pedido. Cada politica
 * comercial (estudiante, happy hour, fidelidad...) es una estrategia concreta
 * intercambiable en tiempo de ejecucion, sin condicionales regados por el codigo.
 */
public interface EstrategiaDescuento {

    /** Nombre legible de la politica, para mostrar en el ticket. */
    String getNombre();

    /** Monto a descontar sobre el subtotal del pedido (en pesos, >= 0). */
    double calcularDescuento(double subtotal, Pedido pedido);
}
