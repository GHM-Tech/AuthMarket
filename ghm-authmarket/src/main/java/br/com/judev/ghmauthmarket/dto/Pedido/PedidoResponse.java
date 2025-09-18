package br.com.judev.ghmauthmarket.dto.Pedido;


import br.com.judev.ghmauthmarket.entity.ItemPedido;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoResponse(String message/*Long usuarioId,
                              String nomeProduto,
                             Integer quantidade*/) {

}

