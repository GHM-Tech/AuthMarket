package br.com.judev.ghmauthmarket.dto.Pedido;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequest(@NotNull List<ItemRequest> itens) {
}
