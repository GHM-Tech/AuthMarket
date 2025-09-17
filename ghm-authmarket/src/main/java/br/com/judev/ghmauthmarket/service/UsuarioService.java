package br.com.judev.ghmauthmarket.service;

import br.com.judev.ghmauthmarket.dto.Usuario.*;
import br.com.judev.ghmauthmarket.entity.Usuario;
import br.com.judev.ghmauthmarket.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public CreateUsuarioResponse criarUsuario(CreateUsuarioRequest request) {
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha());
        usuario.setDataCadastro(LocalDateTime.now());

        Usuario salvo = usuarioRepository.save(usuario);

        return new CreateUsuarioResponse(
                salvo.getId(),
                salvo.getNomeCompleto(),
                salvo.getEmail(),
                salvo.getDataCadastro()
        );
    }

    public AuthResponse loginUsario(AuthRequest request){
         Usuario usuario = usuarioRepository.findByEmail(request.email())
                 .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado pelo email"));

         if(usuario == null || !usuario.getSenha().equals(request.senha())){
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
