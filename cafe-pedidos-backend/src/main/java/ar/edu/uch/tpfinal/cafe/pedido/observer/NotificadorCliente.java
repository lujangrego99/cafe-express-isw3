package ar.edu.uch.tpfinal.cafe.pedido.observer;

import ar.edu.uch.tpfinal.cafe.pedido.Pedido;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Patron OBSERVER - ConcreteObserver.
 *
 * Genera los avisos que veria el cliente (en una app real serian push / SMS).
 * Aca se guardan los ultimos mensajes para mostrarlos en la UI como historial
 * de notificaciones, demostrando que el patron desacopla "que paso" de "a quien
 * se le avisa".
 */
@Component
public class NotificadorCliente implements ObservadorPedido {

    private static final int MAX_MENSAJES = 30;
    private final Deque<String> mensajes = new ArrayDeque<>();

    @Override
    public void actualizar(Pedido pedido) {
        String texto = switch (pedido.getEstado().getNombre()) {
            case "EN_PREPARACION" -> "Pedido #" + pedido.getId() + ": estamos preparando tu cafe.";
            case "LISTO" -> "Pedido #" + pedido.getId() + ": listo para retirar en la barra.";
            case "ENTREGADO" -> "Pedido #" + pedido.getId() + ": entregado. Gracias!";
            case "CANCELADO" -> "Pedido #" + pedido.getId() + ": fue cancelado.";
            default -> "Pedido #" + pedido.getId() + ": recibido.";
        };
        synchronized (mensajes) {
            mensajes.addFirst(texto);
            while (mensajes.size() > MAX_MENSAJES) {
                mensajes.removeLast();
            }
        }
    }

    /** Ultimas notificaciones, de la mas nueva a la mas vieja. */
    public List<String> getMensajes() {
        synchronized (mensajes) {
            return new ArrayList<>(mensajes);
        }
    }
}
