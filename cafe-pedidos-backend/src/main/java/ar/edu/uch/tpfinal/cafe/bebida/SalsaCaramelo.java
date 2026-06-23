package ar.edu.uch.tpfinal.cafe.bebida;

/** Patron DECORATOR - ConcreteDecorator: salsa de caramelo. */
public class SalsaCaramelo extends AdicionalDecorator {

    public SalsaCaramelo(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescripcion() {
        return bebida.getDescripcion() + " + Salsa de caramelo";
    }

    @Override
    public double getCosto() {
        return bebida.getCosto() + 400;
    }
}
