package br.com.alura.comex.repository;

import br.com.alura.comex.controller.PedidosController;
import br.com.alura.comex.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Iterable<?> findByClienteId(Long id);

    Iterable<?> findByIdAndClienteId(Long id, Long idCliente);
}
