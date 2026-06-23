package ar.edu.uch.tpfinal.cafe.web.dto;

import java.util.List;

/**
 * Vista de un pedido para el frontend. Incluye {@code accionesPermitidas}, que el
 * State del pedido informa para que la UI habilite solo los botones validos.
 */
public record PedidoResponse(
        Long id,
        String cliente,
        String estado,
        List<ItemResponse> items,
        double subtotal,
        String descuento,
        double montoDescuento,
        double total,
        String creadoEn,
        List<String> accionesPermitidas,
        List<HistorialResponse> historial) {

    public record ItemResponse(
            String descripcion,
            int cantidad,
            double precioUnitario,
            double subtotal) {
    }

    public record HistorialResponse(
            String estado,
            String momento) {
    }
}
