package br.com.judev.ghmauthmarket.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CreateUsuarioRequest (@NotBlank String nome, @Size(min = 6) String senha, @Email @NotBlank String email){
}
