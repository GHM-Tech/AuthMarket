package br.com.judev.ghmauthmarket.dto.Produto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateProdutoResponse(Long id, String message, String descricao,BigDecimal preco, int quantidade,
                                    LocalDate dataCadastro, Long usuarioId, String nomeCompleto) {
}
