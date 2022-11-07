package br.com.alura.comex.repository;

import br.com.alura.comex.dto.RelatorioPedido;
import br.com.alura.comex.entity.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long> {
    @Query(value = "SELECT c.nome as categoria, count(i.quantidade) as quantidade, sum(i.quantidade * i.preco_unitario) as total FROM categorias c " +
            "JOIN produtos p ON p.categoria_id = c.id " +
            "JOIN itens_pedido i ON p.id=i.produto_id GROUP BY c.id", nativeQuery = true)
    public List<RelatorioPedido> getRelatorioPedido();
}
