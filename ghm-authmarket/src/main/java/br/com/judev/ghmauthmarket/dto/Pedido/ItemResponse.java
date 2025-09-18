package br.com.judev.ghmauthmarket.dto.Pedido;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ItemResponse(
        Long produtoId,
        String nomeProduto,
        Integer quantidade
) {}

