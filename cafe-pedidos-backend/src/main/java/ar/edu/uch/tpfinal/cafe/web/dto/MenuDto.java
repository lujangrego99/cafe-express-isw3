package ar.edu.uch.tpfinal.cafe.web.dto;

import java.util.List;

/**
 * Catalogo que consume el frontend para armar el menu: bebidas base, adicionales
 * (decorators) y politicas de descuento (strategies) disponibles.
 */
public record MenuDto(
        List<Opcion> bases,
        List<Opcion> adicionales,
        List<OpcionDescuento> descuentos) {

    /** Bebida base o adicional, con su precio. */
    public record Opcion(String id, String nombre, double precio) {
    }

    /** Politica de descuento ofrecida al cliente. */
    public record OpcionDescuento(String id, String nombre, String detalle) {
    }
}
