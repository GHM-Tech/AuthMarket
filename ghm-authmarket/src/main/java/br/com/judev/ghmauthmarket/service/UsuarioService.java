package br.com.judev.ghmauthmarket.service;

import br.com.judev.ghmauthmarket.dto.Usuario.*;
import br.com.judev.ghmauthmarket.entity.Usuario;
import br.com.judev.ghmauthmarket.infra.security.TokenService;
import br.com.judev.ghmauthmarket.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
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

        String hash = passwordEncoder.encode(request.senha());
        usuario.setSenha(hash);

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

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("Senha Inválida");
        }

        String token = tokenService.generateToken(usuario);
        return new AuthResponse(token, "Login realizado com Sucesso!");

    }

    public AtualizaSenhaResponse atualizarSenha(Usuario usuarioAutenticado, AtualizaSenhaRequest request) throws AccessDeniedException {
        if (!usuarioAutenticado.getEmail().equals(request.email())) {
            throw new AccessDeniedException("Você não pode atualizar a senha de outro usuário");
        }

        if (!passwordEncoder.matches(request.senhaAtual(), usuarioAutenticado.getSenha())) {
            throw new BadCredentialsException("Senha atual incorreta!");
        }

        usuarioAutenticado.setSenha(passwordEncoder.encode(request.novaSenha()));
        usuarioRepository.save(usuarioAutenticado);

        return new AtualizaSenhaResponse("Senha atualizada com sucesso!");
    }

    public void deletarUsuario(Usuario usuarioAutenticado, Long idUsuario) throws AccessDeniedException {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new EntityNotFoundException("Usuário não encontrado com id: " + idUsuario);
        }

        if (!usuarioAutenticado.getId().equals(idUsuario)) {
            throw new AccessDeniedException("Você não pode deletar outro usuário");
        }

        usuarioRepository.deleteById(idUsuario);
    }
}
