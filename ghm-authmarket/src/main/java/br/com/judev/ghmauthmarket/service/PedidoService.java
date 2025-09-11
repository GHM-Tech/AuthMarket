package br.com.judev.ghmauthmarket.service;

import br.com.judev.ghmauthmarket.entity.ItemPedido;
import br.com.judev.ghmauthmarket.entity.Pedido;
import br.com.judev.ghmauthmarket.entity.Produto;
import br.com.judev.ghmauthmarket.entity.Usuario;
import br.com.judev.ghmauthmarket.repository.PedidoRepository;
import br.com.judev.ghmauthmarket.repository.ProdutoRepository;
import br.com.judev.ghmauthmarket.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

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

    public PedidoResponse criarPedido(PedidoRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        Pedido pedido = new Pedido(usuario);

        request.itens().forEach(itemReq -> {
            Produto produto = produtoRepository.findById(itemReq.produtoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado: " + itemReq.produtoId()));
            ItemPedido item = new ItemPedido(produto, itemReq.quantidade());
            pedido.adicionarItem(item);
        });

        Pedido salvo = pedidoRepository.save(pedido);
        return toResponse(salvo);
    }
}
