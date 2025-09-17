package br.com.judev.ghmauthmarket.controller;

import br.com.judev.ghmauthmarket.dto.Pedido.PedidoRequest;
import br.com.judev.ghmauthmarket.dto.Pedido.PedidoResponse;
import br.com.judev.ghmauthmarket.entity.Pedido;
import br.com.judev.ghmauthmarket.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<PedidoResponse> listagemPedidos(){
        return ResponseEntity.ok(pedidoService.listarTodos()).getBody();
    };


    @PostMapping("/register/{usuarioId}")
    public ResponseEntity<PedidoResponse> criarPedido(
            @PathVariable("usuarioId") Long usuarioId, @Valid @RequestBody PedidoRequest request){
        PedidoResponse response = pedidoService.criarPedido(usuarioId, request);
        return ResponseEntity.status(201).body(response);
    };

}
