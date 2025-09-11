package br.com.judev.ghmauthmarket.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(@NotBlank String senha, @Email String email) {
}
