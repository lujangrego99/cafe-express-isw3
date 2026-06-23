package ar.edu.uch.tpfinal.cafe.web;

import ar.edu.uch.tpfinal.cafe.pedido.estado.TransicionInvalidaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Traduce las excepciones del dominio a respuestas HTTP claras. En particular,
 * una transicion de estado invalida (patron State) devuelve 409 Conflict con un
 * mensaje que la UI muestra al usuario.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransicionInvalidaException.class)
    public ResponseEntity<Map<String, String>> transicionInvalida(TransicionInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> noEncontrado(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> argumentoInvalido(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }
}
