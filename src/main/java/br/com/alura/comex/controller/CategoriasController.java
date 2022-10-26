package br.com.alura.comex.controller;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import br.com.alura.comex.entity.Categoria;
import br.com.alura.comex.dto.RelatorioPedido;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.comex.dto.Dto;
import br.com.alura.comex.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriasController extends CrudController<CategoriaRepository> {

    public CategoriasController(CategoriaRepository repository) {
        super(repository);
    }

    @GetMapping("/pedidos")
    public List<RelatorioPedido> report() {
        return repository.getRelatorioPedido();
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid RequestDto form, UriComponentsBuilder uriBuilder) {
        
        return super.insert(form, "/api/categorias/{id}", uriBuilder);
    }

    @GetMapping("/p")
    public Iterable<?> list(Optional<Integer> page, Optional<Integer> size) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), Sort.by(Sort.DEFAULT_DIRECTION, "nome"));
        Iterable<?> entities = repository.findAll(pageable);
        return entities;//getDtos(entities);

    }


    public void doUpdate (Object object, Dto dto) {
        Categoria entity = (Categoria) object;
        RequestDto form = (RequestDto) dto;
        entity.setNome(form.getNome());
    }

    public static class RequestDto extends Dto<Categoria, Long> {

        private Long id;
        @Size (min = 2)
        private String nome;
        
        public RequestDto() {
        }
    
        public Categoria toEntity() {
            Categoria entity = new Categoria();
            entity.setNome(nome);
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
    
    }
}
