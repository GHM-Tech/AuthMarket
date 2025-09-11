package br.com.judev.ghmauthmarket.dto.Pedido;

import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        LocalDateTime dataPedido,
        String usuarioEmail,
        List<ItemResponse> itens
) {
}
