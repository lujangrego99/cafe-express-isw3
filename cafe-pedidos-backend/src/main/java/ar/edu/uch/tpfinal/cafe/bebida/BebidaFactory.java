package ar.edu.uch.tpfinal.cafe.bebida;

import java.util.List;

/**
 * Auxiliar que arma la cadena de DECORATORS a partir de los identificadores que
 * llegan desde el frontend (ej. base "lat" + adicionales ["shot","vainilla"]).
 *
 * No es uno de los cuatro patrones evaluados: es el punto donde el resto del
 * sistema deja de conocer las clases concretas de adicionales y solo trabaja
 * contra la interfaz {@link Bebida}.
 */
public final class BebidaFactory {

    private BebidaFactory() {
    }

    /** Crea la bebida base (ConcreteComponent) segun su id de catalogo. */
    public static Bebida crearBase(String baseId) {
        return switch (baseId) {
            case "esp" -> new Espresso();
            case "ame" -> new Americano();
            case "lat" -> new Latte();
            case "cap" -> new Capuchino();
            case "chai" -> new TeChai();
            default -> throw new IllegalArgumentException("Bebida base desconocida: " + baseId);
        };
    }

    /** Envuelve la bebida recibida con el adicional (Decorator) indicado. */
    public static Bebida agregarAdicional(Bebida bebida, String adicionalId) {
        return switch (adicionalId) {
            case "shot" -> new ShotExtra(bebida);
            case "crema" -> new CremaBatida(bebida);
            case "vainilla" -> new JarabeVainilla(bebida);
            case "vegetal" -> new LecheVegetal(bebida);
            case "caramelo" -> new SalsaCaramelo(bebida);
            default -> throw new IllegalArgumentException("Adicional desconocido: " + adicionalId);
        };
    }

    /**
     * Construye una bebida completa: toma la base y le aplica, en orden, todos
     * los adicionales pedidos. El resultado es una pila de decorators.
     */
    public static Bebida construir(String baseId, List<String> adicionalesIds) {
        Bebida bebida = crearBase(baseId);
        if (adicionalesIds != null) {
            for (String adicionalId : adicionalesIds) {
                bebida = agregarAdicional(bebida, adicionalId);
            }
        }
        return bebida;
    }
}
