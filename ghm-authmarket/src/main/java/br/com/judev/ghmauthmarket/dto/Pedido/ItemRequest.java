package br.com.judev.ghmauthmarket.dto.Pedido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemRequest(
        @NotNull Long produtoId,
        @NotNull int quantidade
) {}
