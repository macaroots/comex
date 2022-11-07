package br.com.alura.comex.controller;

import br.com.alura.comex.dto.Dto;
import br.com.alura.comex.entity.*;
import br.com.alura.comex.repository.ItemPedidoRepository;
import br.com.alura.comex.repository.PedidoRepository;
import br.com.alura.comex.security.AuthTokenFilter;
import br.com.alura.comex.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController extends CrudController<PedidoRepository> {

    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private TokenService tokenService;
    public PedidosController(PedidoRepository repository, ItemPedidoRepository itemPedidoRepository) {
        super(repository);
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @GetMapping
    public ResponseEntity<?> list(HttpServletRequest request) {
        Long id = tokenService.getIdUsuario(AuthTokenFilter.getToken(request));
        Iterable<?> entities = repository.findByClienteId(id);
        return ResponseEntity.ok(getDtos(entities));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long idCliente = tokenService.getIdUsuario(AuthTokenFilter.getToken(request));
            Object entity = repository.findByIdAndClienteId(id, idCliente).iterator().next();
            return ResponseEntity.ok(getDetailedDto(entity));
        } catch (EntityNotFoundException | NoSuchElementException e) {
            /*
            No caso de ser de outro cliente, poderia devolver FORBIDDEN,
            mas NOT FOUND se encaixa bem também e não dá a dica que o id existe.
             */
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return super.delete(id);
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
