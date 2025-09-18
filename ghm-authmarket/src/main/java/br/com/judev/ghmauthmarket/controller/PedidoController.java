package br.com.judev.ghmauthmarket.controller;

import br.com.judev.ghmauthmarket.dto.Pedido.GetPedidoResponse;
import br.com.judev.ghmauthmarket.dto.Pedido.PedidoRequest;
import br.com.judev.ghmauthmarket.dto.Pedido.PedidoResponse;
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

    @PostMapping("/register")
    public ResponseEntity<PedidoResponse> criarPedido(
            @Valid @RequestBody PedidoRequest request) {
        PedidoResponse response = pedidoService.criarPedido(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<GetPedidoResponse>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{idPedido}/produtos/{idProduto}")
    public ResponseEntity<Void> deletarProdutoEmPedidoCo(@PathVariable Long idPedido, @PathVariable Long idProduto) {
        pedidoService.deleteProdutoEmPedido(idPedido, idProduto);
        return ResponseEntity.noContent().build();

    }

}
