package br.com.judev.ghmauthmarket.dto.Pedido;

import java.util.List;

public record GetPedidoResponse(
        Long pedidoId,
        Long usuarioId,
        List<ItemResponse> itens
) {}
