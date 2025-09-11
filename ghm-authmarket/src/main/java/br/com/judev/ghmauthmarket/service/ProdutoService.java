package br.com.judev.ghmauthmarket.service;

import br.com.judev.ghmauthmarket.dto.CreateProdutoRequest;
import br.com.judev.ghmauthmarket.dto.CreateProdutoResponse;
import br.com.judev.ghmauthmarket.entity.Produto;
import br.com.judev.ghmauthmarket.entity.Usuario;
import br.com.judev.ghmauthmarket.repository.PodutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

   private final PodutoRepository produtoRepository;

    public ProdutoService(PodutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public CreateProdutoResponse createProduto(Long idUsuario, CreateProdutoRequest request) {
        produtoRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Produto p = new Produto();
        p.setNome(request.nome());
        p.setDescricao(request.descricao());
        p.setPreco(request.preco());
        p.setQuantidade(request.quantidade());
        var produtoSalvo = produtoRepository.save(p);

        return new CreateProdutoResponse("Produto Criado com Sucesso!");
    }



}
