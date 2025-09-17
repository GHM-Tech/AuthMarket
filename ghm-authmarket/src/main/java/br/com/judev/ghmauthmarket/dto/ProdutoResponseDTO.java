package br.com.judev.ghmauthmarket.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        int quantidade,
        LocalDate dataCadastro,
        Long usuarioId,
        String usuarioNome
) {}
