package ar.edu.uch.tpfinal.cafe.bebida;

/** Patron DECORATOR - ConcreteDecorator: crema batida. */
public class CremaBatida extends AdicionalDecorator {

    public CremaBatida(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescripcion() {
        return bebida.getDescripcion() + " + Crema batida";
    }

    @Override
    public double getCosto() {
        return bebida.getCosto() + 400;
    }
}
