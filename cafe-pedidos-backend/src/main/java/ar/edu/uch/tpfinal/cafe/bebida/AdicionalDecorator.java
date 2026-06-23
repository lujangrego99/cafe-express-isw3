package ar.edu.uch.tpfinal.cafe.bebida;

/**
 * Patron DECORATOR - rol Decorator (abstracto).
 *
 * Mantiene una referencia a la {@link Bebida} que envuelve (puede ser una base
 * o ya otra bebida decorada) y delega en ella. Las subclases concretas agregan
 * descripcion y costo extra. Esto permite encadenar adicionales sin explotar la
 * cantidad de subclases (evita Espresso, EspressoConShot, EspressoConShotYCrema...).
 */
public abstract class AdicionalDecorator implements Bebida {

    protected final Bebida bebida;

    protected AdicionalDecorator(Bebida bebida) {
        this.bebida = bebida;
    }
}
