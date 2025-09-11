package br.com.judev.ghmauthmarket.repository;

import br.com.judev.ghmauthmarket.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
