package br.com.judev.ghmauthmarket.dto.Pedido;

public record ItemResponse(
        Long produtoId,
        String nomeProduto,
        Integer quantidade
) {}
