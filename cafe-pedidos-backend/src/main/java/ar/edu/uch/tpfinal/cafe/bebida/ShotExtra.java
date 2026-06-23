package ar.edu.uch.tpfinal.cafe.bebida;

/** Patron DECORATOR - ConcreteDecorator: shot extra de cafe. */
public class ShotExtra extends AdicionalDecorator {

    public ShotExtra(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescripcion() {
        return bebida.getDescripcion() + " + Shot extra";
    }

    @Override
    public double getCosto() {
        return bebida.getCosto() + 500;
    }
}
