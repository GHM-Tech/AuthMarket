package br.com.judev.ghmauthmarket.controller;

import br.com.judev.ghmauthmarket.dto.Produto.CreateProdutoRequest;
import br.com.judev.ghmauthmarket.dto.Produto.CreateProdutoResponse;
import br.com.judev.ghmauthmarket.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/register")
    public ResponseEntity<CreateProdutoResponse> register(@Valid @RequestBody CreateProdutoRequest produtoRequest){
        CreateProdutoResponse produtoResponse = produtoService.criarProduto(produtoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(produtoResponse);

    }

}
