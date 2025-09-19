package br.com.judev.ghmauthmarket.service;

import br.com.judev.ghmauthmarket.dto.Produto.UpdateProdutoRequest;
import br.com.judev.ghmauthmarket.dto.Produto.UpdateProdutoResponse;
import br.com.judev.ghmauthmarket.dto.Produto.CreateProdutoRequest;
import br.com.judev.ghmauthmarket.dto.Produto.CreateProdutoResponse;
import br.com.judev.ghmauthmarket.entity.Produto;
import br.com.judev.ghmauthmarket.entity.Usuario;
import br.com.judev.ghmauthmarket.repository.ProdutoRepository;
import br.com.judev.ghmauthmarket.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
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

    public CreateProdutoResponse criarProduto(Usuario usuario, CreateProdutoRequest dto) {
        Usuario usuarioGerenciado = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(BigDecimal.valueOf(dto.preco()));
        produto.setQuantidade(dto.quantidade());
        produto.setUsuario(usuarioGerenciado);

        Produto salvo = produtoRepository.save(produto);

        return new CreateProdutoResponse(
                salvo.getId(), salvo.getNome(), salvo.getDescricao(),
                salvo.getPreco(), salvo.getQuantidade(), salvo.getDataCadastro(),
                salvo.getUsuario().getId(), salvo.getUsuario().getNomeCompleto());
    }

    public List<CreateProdutoResponse> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(p -> new CreateProdutoResponse(
                        p.getId(), p.getNome(), p.getDescricao(),
                        p.getPreco(), p.getQuantidade(), p.getDataCadastro(),
                        p.getUsuario().getId(), p.getUsuario().getNomeCompleto()))
                .collect(Collectors.toList());
    }

    public UpdateProdutoResponse atualizarProduto(Long produtoId, UpdateProdutoRequest dto, Usuario usuario) throws AccessDeniedException {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (produto.getUsuario() == null) {
            throw new EntityNotFoundException("Usuário associado ao produto não encontrado");
        }

        if (!produto.getUsuario().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Você não pode atualizar um produto de outro usuário");
        }

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setQuantidade(dto.quantidade());

        produtoRepository.save(produto);

        return new UpdateProdutoResponse("Produto Atualizado com sucesso!");
    }
}
