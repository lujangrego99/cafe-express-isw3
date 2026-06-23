package ar.edu.uch.tpfinal.cafe.web;

import ar.edu.uch.tpfinal.cafe.service.CafeService;
import ar.edu.uch.tpfinal.cafe.web.dto.CrearPedidoRequest;
import ar.edu.uch.tpfinal.cafe.web.dto.PedidoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** API REST de pedidos: alta y transiciones de estado (patron State). */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final CafeService cafeService;

    public PedidoController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> crear(@RequestBody CrearPedidoRequest request) {
        PedidoResponse creado = cafeService.crearPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public List<PedidoResponse> listar() {
        return cafeService.listarPedidos();
    }

    @GetMapping("/{id}")
    public PedidoResponse obtener(@PathVariable Long id) {
        return cafeService.obtenerPedido(id);
    }

    @PostMapping("/{id}/preparar")
    public PedidoResponse preparar(@PathVariable Long id) {
        return cafeService.prepararPedido(id);
    }

    @PostMapping("/{id}/listo")
    public PedidoResponse listo(@PathVariable Long id) {
        return cafeService.marcarListo(id);
    }

    @PostMapping("/{id}/entregar")
    public PedidoResponse entregar(@PathVariable Long id) {
        return cafeService.entregarPedido(id);
    }

    @PostMapping("/{id}/cancelar")
    public PedidoResponse cancelar(@PathVariable Long id) {
        return cafeService.cancelarPedido(id);
    }
}
