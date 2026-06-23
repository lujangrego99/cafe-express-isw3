package ar.edu.uch.tpfinal.cafe.bebida;

/** Patron DECORATOR - ConcreteDecorator: leche vegetal (almendras/avena). */
public class LecheVegetal extends AdicionalDecorator {

    public LecheVegetal(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescripcion() {
        return bebida.getDescripcion() + " + Leche vegetal";
    }

    @Override
    public double getCosto() {
        return bebida.getCosto() + 450;
    }
}
