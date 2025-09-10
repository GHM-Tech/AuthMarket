package br.com.judev.ghmauthmarket.repository;

import br.com.judev.ghmauthmarket.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
