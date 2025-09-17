package br.com.judev.ghmauthmarket.controller;

import br.com.judev.ghmauthmarket.dto.Usuario.*;
import br.com.judev.ghmauthmarket.entity.Usuario;
import br.com.judev.ghmauthmarket.repository.UsuarioRepository;
import br.com.judev.ghmauthmarket.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUsuarioResponse> register(@Valid @RequestBody CreateUsuarioRequest request) {
        CreateUsuarioResponse response = usuarioService.criarUsuario(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        AuthResponse response = usuarioService.loginUsario(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/atualiza-senha")
    public ResponseEntity<AtualizaSenhaResponse> atualizarSenha(@Valid @RequestBody AtualizaSenhaRequest request) {
        AtualizaSenhaResponse response = usuarioService.atualizarSenha(request.email(), request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long idUsuario) {
        usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }
}