package ar.edu.uch.tpfinal.cafe.bebida;

/**
 * Patron DECORATOR - rol Component.
 *
 * Define la interfaz comun para las bebidas base (ConcreteComponent) y para los
 * adicionales que las envuelven (Decorator). Gracias a esta interfaz el cliente
 * trata de forma uniforme una bebida simple y una bebida decorada con N extras.
 */
public interface Bebida {

    /** Descripcion legible que se va componiendo con cada adicional. */
    String getDescripcion();

    /** Costo total acumulado (precio base + costo de cada adicional). */
    double getCosto();
}
