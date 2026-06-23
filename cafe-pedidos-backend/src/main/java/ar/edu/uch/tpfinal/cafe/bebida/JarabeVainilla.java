package ar.edu.uch.tpfinal.cafe.bebida;

/** Patron DECORATOR - ConcreteDecorator: jarabe de vainilla. */
public class JarabeVainilla extends AdicionalDecorator {

    public JarabeVainilla(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescripcion() {
        return bebida.getDescripcion() + " + Jarabe de vainilla";
    }

    @Override
    public double getCosto() {
        return bebida.getCosto() + 350;
    }
}
