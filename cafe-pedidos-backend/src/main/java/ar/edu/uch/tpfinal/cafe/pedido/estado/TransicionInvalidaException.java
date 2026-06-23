package ar.edu.uch.tpfinal.cafe.pedido.estado;

/**
 * Se lanza cuando se intenta una transicion de estado no permitida
 * (ej. entregar un pedido que todavia esta EN_PREPARACION). El controlador la
 * traduce a un HTTP 409 Conflict con un mensaje claro para el usuario.
 */
public class TransicionInvalidaException extends RuntimeException {
    public TransicionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
