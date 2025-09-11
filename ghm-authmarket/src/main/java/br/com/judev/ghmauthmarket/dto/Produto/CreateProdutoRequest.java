package br.com.judev.ghmauthmarket.dto.Produto;

import jakarta.validation.constraints.*;

public record CreateProdutoRequest (

    @NotBlank(message = "O nome do produto é obrigatório")
     String nome,

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 200, message = "A descrição não pode passar de 200 caracteres")
     String descricao,

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que 0")
     Double preco,

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    Integer quantidade){

 //   Long usuario_id){

}

