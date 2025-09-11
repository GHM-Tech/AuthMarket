package br.com.judev.ghmauthmarket.service;

import br.com.judev.ghmauthmarket.dto.Usuario.AuthRequest;
import br.com.judev.ghmauthmarket.dto.Usuario.AuthResponse;
import br.com.judev.ghmauthmarket.dto.Usuario.CreateUsuarioRequest;
import br.com.judev.ghmauthmarket.dto.Usuario.CreateUsuarioResponse;
import br.com.judev.ghmauthmarket.entity.Usuario;
import br.com.judev.ghmauthmarket.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public CreateUsuarioResponse registerUsuario(CreateUsuarioRequest request) {
        Usuario u = new Usuario();
        u.setNomeCompleto(request.nome());
        u.setEmail(request.email());
        u.setSenha(request.senha());
        var usuarioCriado = usuarioRepository.save(u);
        return new CreateUsuarioResponse("Usuário Criado com Sucesso");
    }

    public AuthResponse loginUsario(AuthRequest request){
         Usuario usuario = usuarioRepository.findByEmail(request.email())
                 .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado pelo email"));

         if(usuario == null || usuario.getSenha().equals(request.senha())){
             throw new EntityNotFoundException("Usuário inexistente ou Senha incorreta!");
         }
         return new AuthResponse("Login realizado com Sucesso!");

    }
}
