package br.com.judev.ghmauthmarket.service;

import br.com.judev.ghmauthmarket.dto.Usuario.*;
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
        usuarioRepository.save(u);
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

    public AtualizaSenhaResponse atualizarSenha(String email, AtualizaSenhaRequest request){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado pelo email"));

        if(usuario.getSenha().equals(request.senhaAtual())){
            throw new EntityNotFoundException("Senha incorreta!");
        }
        usuario.setSenha(request.novaSenha());
         usuarioRepository.save(usuario);
        return new AtualizaSenhaResponse("Senha atualizada com Sucesso!");
    }

    public void deletarUsuario(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new EntityNotFoundException("Usuário não encontrado com id: " + idUsuario);
        }
        usuarioRepository.deleteById(idUsuario);
    }
}
