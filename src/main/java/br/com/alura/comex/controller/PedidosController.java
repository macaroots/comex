package br.com.alura.comex.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import br.com.alura.comex.entity.*;
import br.com.alura.comex.repository.ItemPedidoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.comex.dto.Dto;
import br.com.alura.comex.repository.PedidoRepository;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController extends CrudController<PedidoRepository> {

    private ItemPedidoRepository itemPedidoRepository;
    public PedidosController(PedidoRepository repository, ItemPedidoRepository itemPedidoRepository) {
        super(repository);
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid RequestDto form, UriComponentsBuilder uriBuilder) {
        form.setItemPedidoRepository(itemPedidoRepository);
        return super.insert(form, "/api/pedidos/{id}", uriBuilder);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid RequestDto form) {
        return super.update(id, form);
    }
    public void doUpdate (Object object, Dto dto) {
        Pedido entity = (Pedido) object;
        RequestDto form = (RequestDto) dto;
        form.toEntity(entity);
    }

    public static class RequestDto extends Dto<Pedido, Long> {
        private Long id_cliente;
        private BigDecimal desconto = BigDecimal.ZERO;
        private TipoDesconto tipoDesconto = TipoDesconto.NENHUM;
        private List<ProdutoDto> produtos;

        private ItemPedidoRepository itemPedidoRepository;
    
        public Pedido toEntity() {

            Pedido entity = new Pedido();
            getProdutos().forEach(p -> {
                ItemDePedido item = new ItemDePedido(p.getQuantidade(), new Produto(p.getId(), p.getPrecoUnitario()));
                item.setDesconto(p.getDesconto());
                item.setTipoDesconto(p.getTipoDesconto());
                itemPedidoRepository.save(item);
            });
            return toEntity(entity);
        }
        public Pedido toEntity(Pedido entity) {
            entity.setCliente(new Cliente(id_cliente));
            entity.setDesconto(desconto);
            entity.setTipoDesconto(tipoDesconto);
            return entity;
        }
        public Long getId_cliente() {
            return id_cliente;
        }
        public void setId_cliente(Long id_cliente) {
            this.id_cliente = id_cliente;
        }
        public BigDecimal getDesconto() {
            return desconto;
        }
        public void setDesconto(BigDecimal desconto) {
            this.desconto = desconto;
        }
        public TipoDesconto getTipoDesconto() {
            return tipoDesconto;
        }
        public void setTipoDesconto(TipoDesconto tipoDesconto) {
            this.tipoDesconto = tipoDesconto;
        }

        public List<ProdutoDto> getProdutos() {
            return produtos;
        }

        public void setProdutos(List<ProdutoDto> produtos) {
            this.produtos = produtos;
        }

        public ItemPedidoRepository getItemPedidoRepository() {
            return itemPedidoRepository;
        }

        public void setItemPedidoRepository(ItemPedidoRepository itemPedidoRepository) {
            this.itemPedidoRepository = itemPedidoRepository;
        }
    }
    public static class ProdutoDto {
        private Long id;
        private int quantidade;
        private BigDecimal precoUnitario;
        private BigDecimal desconto = BigDecimal.ZERO;
        private TipoDescontoItem tipoDesconto = TipoDescontoItem.NENHUM;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public BigDecimal getPrecoUnitario() {
            return precoUnitario;
        }

        public void setPrecoUnitario(BigDecimal precoUnitario) {
            this.precoUnitario = precoUnitario;
        }

        public BigDecimal getDesconto() {
            return desconto;
        }

        public void setDesconto(BigDecimal desconto) {
            this.desconto = desconto;
        }

        public TipoDescontoItem getTipoDesconto() {
            return tipoDesconto;
        }

        public void setTipoDesconto(TipoDescontoItem tipoDesconto) {
            this.tipoDesconto = tipoDesconto;
        }
    }
}
