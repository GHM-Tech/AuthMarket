package br.com.judev.ghmauthmarket.dto.Usuario;

import java.time.LocalDateTime;

public record CreateUsuarioResponse(
        Long id,
        String nomeCompleto,
        String email,
        LocalDateTime dataCadastro
) {}
