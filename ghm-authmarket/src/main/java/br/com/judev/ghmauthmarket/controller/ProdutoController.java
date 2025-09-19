package br.com.judev.ghmauthmarket.controller;


import br.com.judev.ghmauthmarket.dto.Pedido.PedidoResponse;
import br.com.judev.ghmauthmarket.dto.Produto.UpdateProdutoRequest;
import br.com.judev.ghmauthmarket.dto.Produto.UpdateProdutoResponse;
import br.com.judev.ghmauthmarket.dto.Produto.CreateProdutoRequest;
import br.com.judev.ghmauthmarket.dto.Produto.CreateProdutoResponse;
import br.com.judev.ghmauthmarket.dto.ProdutoResponseDTO;
import br.com.judev.ghmauthmarket.entity.Usuario;
import br.com.judev.ghmauthmarket.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateProdutoResponse> register(
            @AuthenticationPrincipal Usuario usuario,
            @Valid @RequestBody CreateProdutoRequest produtoRequest) {

        CreateProdutoResponse produtoResponse = produtoService.criarProduto(usuario, produtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UpdateProdutoResponse> update(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario,
            @RequestBody UpdateProdutoRequest produtoRequest) throws AccessDeniedException {

        UpdateProdutoResponse response = produtoService.atualizarProduto(id, produtoRequest, usuario);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<CreateProdutoResponse>> listarTodos() {
        List<CreateProdutoResponse> produtoResponses = produtoService.listarTodos();
        return ResponseEntity.ok(produtoResponses);
    }
}