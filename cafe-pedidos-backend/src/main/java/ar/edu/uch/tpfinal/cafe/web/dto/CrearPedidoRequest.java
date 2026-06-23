package ar.edu.uch.tpfinal.cafe.web.dto;

import java.util.List;

/**
 * Pedido entrante desde el frontend. Cada item indica la bebida base, los
 * adicionales (ids de decorators) y la cantidad. {@code tipoDescuento} selecciona
 * la estrategia y {@code puntosFidelidad} alimenta el canje de puntos.
 */
public record CrearPedidoRequest(
        String cliente,
        List<ItemRequest> items,
        String tipoDescuento,
        int puntosFidelidad) {

    public record ItemRequest(
            String baseId,
            List<String> adicionales,
            int cantidad) {
    }
}
