package ar.edu.uch.tpfinal.cafe.descuento;

/**
 * Resuelve la {@link EstrategiaDescuento} concreta a partir del identificador que
 * elige el usuario en la UI. Centraliza el unico lugar donde se conocen las
 * estrategias concretas.
 */
public final class EstrategiaDescuentoFactory {

    private EstrategiaDescuentoFactory() {
    }

    public static EstrategiaDescuento desde(String tipo) {
        if (tipo == null) {
            return new SinDescuento();
        }
        return switch (tipo) {
            case "estudiante" -> new DescuentoEstudiante();
            case "happyhour" -> new DescuentoHappyHour();
            case "fidelidad" -> new DescuentoFidelidad();
            case "ninguno", "" -> new SinDescuento();
            default -> throw new IllegalArgumentException("Tipo de descuento desconocido: " + tipo);
        };
    }
}
