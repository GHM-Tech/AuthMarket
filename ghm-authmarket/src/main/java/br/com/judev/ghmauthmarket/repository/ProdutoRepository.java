package br.com.judev.ghmauthmarket.repository;

import br.com.judev.ghmauthmarket.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
import br.com.judev.ghmauthmarket.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
//     Optional <Usuario> findByIdUsuario(Long id_usuario);
}
