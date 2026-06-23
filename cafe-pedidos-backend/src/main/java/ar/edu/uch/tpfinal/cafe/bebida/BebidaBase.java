package ar.edu.uch.tpfinal.cafe.bebida;

/**
 * Patron DECORATOR - base de los ConcreteComponent.
 *
 * Representa una bebida sin adicionales. Las subclases concretas (Espresso,
 * Americano, etc.) solo aportan su nombre y precio base.
 */
public abstract class BebidaBase implements Bebida {

    private final String nombre;
    private final double precioBase;

    protected BebidaBase(String nombre, double precioBase) {
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    @Override
    public String getDescripcion() {
        return nombre;
    }

    @Override
    public double getCosto() {
        return precioBase;
    }
}
