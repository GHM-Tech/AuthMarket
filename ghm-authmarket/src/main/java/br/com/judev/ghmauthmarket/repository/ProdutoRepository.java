package br.com.judev.ghmauthmarket.repository;

import br.com.judev.ghmauthmarket.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
}
