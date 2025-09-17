package br.com.judev.ghmauthmarket.repository;

import br.com.judev.ghmauthmarket.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    List<Produto> findByUsuarioId(Long usuarioId);

}
