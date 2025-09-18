package br.com.judev.ghmauthmarket.dto.Pedido;


import br.com.judev.ghmauthmarket.entity.ItemPedido;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoResponse(@NotNull Long usuarioId,
                             @NotEmpty List<ItemPedido> itens) {
}

