package br.com.judev.ghmauthmarket.dto.Produto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProdutoRequest(
        @NotBlank String nome,
        @NotBlank @Size(max = 200) String descricao,
        @NotNull @DecimalMin("0.01") double preco,
        @NotNull @Min(0) int quantidade,
        @NotNull Long usuarioId
) {}

