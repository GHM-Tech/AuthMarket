package br.com.judev.ghmauthmarket.controller;

import br.com.judev.ghmauthmarket.dto.Usuario.AuthRequest;
import br.com.judev.ghmauthmarket.dto.Usuario.AuthResponse;
import br.com.judev.ghmauthmarket.dto.Usuario.CreateUsuarioRequest;
import br.com.judev.ghmauthmarket.dto.Usuario.CreateUsuarioResponse;
import br.com.judev.ghmauthmarket.repository.UsuarioRepository;
import br.com.judev.ghmauthmarket.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUsuarioResponse> register(@Valid @RequestBody CreateUsuarioRequest request) {
        CreateUsuarioResponse response = usuarioService.registerUsuario(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        AuthResponse response = usuarioService.loginUsario(request);
        return ResponseEntity.ok(response);
    }
}
