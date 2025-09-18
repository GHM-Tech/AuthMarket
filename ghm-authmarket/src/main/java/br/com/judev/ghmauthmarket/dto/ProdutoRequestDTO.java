package br.com.judev.ghmauthmarket.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank String nome,
        @NotBlank @Size(max = 200) String descricao,
        @NotNull @DecimalMin("0.01") BigDecimal preco,
        @NotNull @Min(0) Integer quantidade,
        @NotNull Long usuarioId
) {}
