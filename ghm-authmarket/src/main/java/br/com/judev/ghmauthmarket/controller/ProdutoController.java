package br.com.judev.ghmauthmarket.controller;


import br.com.judev.ghmauthmarket.dto.Produto.UpdateProdutoRequest;
import br.com.judev.ghmauthmarket.dto.Produto.UpdateProdutoResponse;
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

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateProdutoResponse> register(@Valid @RequestBody CreateProdutoRequest produtoRequest){
        CreateProdutoResponse produtoResponse = produtoService.criarProduto(produtoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(produtoResponse);
 
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateProdutoResponse> update(@PathVariable Long id,
                                                        @RequestBody UpdateProdutoRequest produtoRequest) {
        UpdateProdutoResponse updateProdutoResponse = produtoService.atualizarProduto(id, produtoRequest);
        if (updateProdutoResponse == null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(produtoService.atualizarProduto(id, produtoRequest));
    }

        @DeleteMapping("/{id}")
        public ResponseEntity<CreateProdutoResponse> delete(@PathVariable Long id){
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
        }

}