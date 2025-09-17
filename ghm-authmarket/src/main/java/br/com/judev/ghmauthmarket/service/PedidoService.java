package br.com.judev.ghmauthmarket.service;

import br.com.judev.ghmauthmarket.dto.Pedido.ItemResponse;
import br.com.judev.ghmauthmarket.dto.Pedido.PedidoRequest;
import br.com.judev.ghmauthmarket.dto.Pedido.PedidoResponse;
import br.com.judev.ghmauthmarket.entity.ItemPedido;
import br.com.judev.ghmauthmarket.entity.Pedido;
import br.com.judev.ghmauthmarket.entity.Produto;
import br.com.judev.ghmauthmarket.entity.Usuario;
import br.com.judev.ghmauthmarket.repository.PedidoRepository;
import br.com.judev.ghmauthmarket.repository.ProdutoRepository;
import br.com.judev.ghmauthmarket.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;


    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }

    public PedidoResponse criarPedido(Long usuarioId, PedidoRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);

        // Mount the list of ItemPedido from the request
        List<ItemPedido> itens = request.itens().stream()
                .map(itemReq -> {
                    Produto produto = produtoRepository.findById(itemReq.getProduto().getId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Produto não encontrado: " + itemReq.getProduto().getId()));

                    if (produto.getQuantidade() < itemReq.getQuantidade()) {
                        throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
                    }

                    produto.setQuantidade(produto.getQuantidade() - itemReq.getQuantidade());
                    produtoRepository.save(produto);

                    return new ItemPedido(produto, itemReq.getQuantidade());
                })
                .toList();

        // Associate items with the order
        itens.forEach(pedido::adicionarItem);

        // Calculate total
        BigDecimal total = itens.stream()
                .map(i -> i.getProduto().getPreco().multiply(BigDecimal.valueOf(i.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setValorTotal(total);

        Pedido salvo = pedidoRepository.save(pedido);
        return toResponse(salvo);
    }

    private PedidoResponse toResponse(Pedido pedido) {
        List<ItemResponse> itens = pedido.getItens().stream()
                .map(item -> new ItemResponse(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade()))
                .collect(Collectors.toList());

        return new PedidoResponse(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getUsuario().getEmail(),
                itens,
                pedido.getValorTotal()
        );

    }
    public List<PedidoResponse> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<PedidoResponse> listarPorUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new EntityNotFoundException("Usuário não encontrado com ID: " + usuarioId);
        }

        return pedidoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
