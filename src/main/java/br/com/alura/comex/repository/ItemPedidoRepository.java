package br.com.alura.comex.repository;

import br.com.alura.comex.entity.ItemDePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemDePedido, Long> {

}
