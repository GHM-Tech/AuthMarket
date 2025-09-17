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


}
