package ar.edu.uch.tpfinal.cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicacion CafeExpress.
 *
 * TP Final Integrador - Ingenieria de Software III (UCH).
 * Modulo full-stack de pedidos de cafeteria que integra cuatro patrones GOF:
 * Decorator, State, Strategy y Observer.
 */
@SpringBootApplication
public class CafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeApplication.class, args);
    }
}
