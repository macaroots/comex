package br.com.alura.comex.controller;

import br.com.alura.comex.entity.Categoria;
import br.com.alura.comex.entity.Produto;
import br.com.alura.comex.dto.Dto;
import br.com.alura.comex.repository.ProdutoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutosController extends CrudController<ProdutoRepository> {

    public ProdutosController(ProdutoRepository repository) {
        super(repository);
    }

    @GetMapping
    public Iterable<?> list() {
        return super.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return super.get(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid RequestDto form, UriComponentsBuilder uriBuilder) {
        return super.insert(form, "/api/produtos/{id}", uriBuilder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid RequestDto form) {
        return super.update(id, form);
    }
    
    public void doUpdate (Object object, Dto dto) {
        Produto entity = (Produto) object;
        RequestDto form = (RequestDto) dto;
        form.toEntity(entity);
    }

    @Override
    public Object getDto(Object entity) {
        return new ResponseDto((Produto) entity);
    }/*/
    @Override
    public Object getDetailedDto(Object entity) {
        return new DetailedDto((Produto) entity);
    }/**/

    public record ResponseDto(
            Long id,
            String nome,
            BigDecimal precoUnitario,
            CategoriaDto categoria) {
        public ResponseDto(Produto produto) {
            this(
                    produto.getId(),
                    produto.getNome(),
                    produto.getPrecoUnitario(),
                    new CategoriaDto(produto.getCategoria()));
        }
    }
    public record CategoriaDto(Long id, String nome) {
        public CategoriaDto(Categoria categoria) {
            this(
                categoria.getId(), 
                categoria.getNome()
            );
        }
    }
    public record DetailedDto(
            Long id,
            String nome,
            String descricao,
            BigDecimal precoUnitario,
            int quantidade_estoque,
            CategoriaDto categoria) {
        public DetailedDto(Produto produto) {
            this(
                    produto.getId(),
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getPrecoUnitario(),
                    produto.getQuantidadeEstoque(),
                    new CategoriaDto(produto.getCategoria()));
        }
    }


    public static class RequestDto extends Dto<Produto, Long> {

        private Long id;
    
        private String nome;
    
        private String descricao;

        private BigDecimal precoUnitario;

        private int quantidadeEstoque;

        private Long id_categoria;
    
        public Produto toEntity() {
            Produto entity = new Produto();
            return toEntity(entity);
        }
        public Produto toEntity(Produto entity) {
            entity.setNome(nome);
            entity.setDescricao(descricao);
            entity.setPrecoUnitario(precoUnitario);
            entity.setQuantidadeEstoque(quantidadeEstoque);
            entity.setCategoria(new Categoria(id_categoria));
            return entity;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public BigDecimal getPrecoUnitario() {
            return precoUnitario;
        }

        public void setPrecoUnitario(BigDecimal precoUnitario) {
            this.precoUnitario = precoUnitario;
        }

        public int getQuantidadeEstoque() {
            return quantidadeEstoque;
        }

        public void setQuantidadeEstoque(int quantidadeEstoque) {
            this.quantidadeEstoque = quantidadeEstoque;
        }

        public Long getId_categoria() {
            return id_categoria;
        }

        public void setId_categoria(Long id_categoria) {
            this.id_categoria = id_categoria;
        }
    
    }
}
