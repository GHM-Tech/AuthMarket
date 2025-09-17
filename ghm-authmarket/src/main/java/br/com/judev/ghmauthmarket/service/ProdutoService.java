package br.com.judev.ghmauthmarket.service;

import br.com.judev.ghmauthmarket.dto.Produto.UpdateProdutoResponse;
import br.com.judev.ghmauthmarket.dto.Produto.CreateProdutoRequest;
import br.com.judev.ghmauthmarket.dto.Produto.CreateProdutoResponse;
import br.com.judev.ghmauthmarket.entity.Produto;
import br.com.judev.ghmauthmarket.repository.ProdutoRepository;
import br.com.judev.ghmauthmarket.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

   private final ProdutoRepository produtoRepository;

   private final UsuarioRepository usuarioRepository;

    public ProdutoService(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public CreateProdutoResponse createProduto(CreateProdutoRequest request) {
//        produtoRepository.findById())
//                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        if (request.nome() == null || request.nome().isEmpty()){
            throw new EntityNotFoundException("Produto não pode ser null ou vazio");
        }

        Produto p = new Produto();
        p.setNome(request.nome());
        p.setDescricao(request.descricao());
        p.setPreco(request.preco());
        p.setQuantidade(request.quantidade());
        var produtoSalvo = produtoRepository.save(p);

        return new CreateProdutoResponse("Produto Criado com Sucesso!");
    }

    public UpdateProdutoResponse updateProduto (Long id){
        Produto produto = produtoRepository.findById(id).get();
        return new  UpdateProdutoResponse("Produto atualizado com sucesso.");
    }

    public void deleteProduto(Long idProduto){
            produtoRepository.deleteById(idProduto);
        }
 }
