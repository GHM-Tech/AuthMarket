package br.com.judev.ghmauthmarket.service;

import br.com.judev.ghmauthmarket.dto.Produto.UpdateProdutoRequest;
import br.com.judev.ghmauthmarket.dto.Produto.UpdateProdutoResponse;
import br.com.judev.ghmauthmarket.dto.Produto.CreateProdutoRequest;
import br.com.judev.ghmauthmarket.dto.Produto.CreateProdutoResponse;
import br.com.judev.ghmauthmarket.dto.ProdutoRequestDTO;
import br.com.judev.ghmauthmarket.dto.ProdutoResponseDTO;
import br.com.judev.ghmauthmarket.entity.Produto;
import br.com.judev.ghmauthmarket.entity.Usuario;
import br.com.judev.ghmauthmarket.repository.ProdutoRepository;
import br.com.judev.ghmauthmarket.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;


    public ProdutoService(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public CreateProdutoResponse criarProduto(CreateProdutoRequest dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário (dono) não encontrado"));

        Produto p = new Produto();
        p.setNome(dto.nome());
        p.setDescricao(dto.descricao());
        p.setPreco(BigDecimal.valueOf(dto.preco()));
        p.setQuantidade(dto.quantidade());
        p.setUsuario(usuario);

        return new CreateProdutoResponse(
                p.getId(), p.getNome(), p.getDescricao(),
                p.getPreco(), p.getQuantidade(), p.getDataCadastro(),
                p.getUsuario().getId(), p.getUsuario().getNomeCompleto());
    }

   public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(p -> new ProdutoResponseDTO(
                        p.getId(), p.getNome(), p.getDescricao(),
                        p.getPreco(), p.getQuantidade(), p.getDataCadastro(),
                        p.getUsuario().getId(), p.getUsuario().getNomeCompleto()))
                .collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> listarPorUsuario(Long usuarioId) {
        return produtoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(p -> new ProdutoResponseDTO(
                        p.getId(), p.getNome(), p.getDescricao(),
                        p.getPreco(), p.getQuantidade(), p.getDataCadastro(),
                        p.getUsuario().getId(), p.getUsuario().getNomeCompleto()))
                .collect(Collectors.toList());
    }

    public UpdateProdutoResponse atualizarProduto(Long produtoId, UpdateProdutoRequest dto) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

         usuarioRepository.findById(produto.getUsuario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQuantidade(dto.quantidade());

        produtoRepository.save(produto);

        return new UpdateProdutoResponse("Produto Atualizado com sucesso!");
    }


    public void deleteProduto(Long idProduto){
            produtoRepository.deleteById(idProduto);
        }
 }
