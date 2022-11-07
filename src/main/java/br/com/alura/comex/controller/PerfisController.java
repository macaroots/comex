package br.com.alura.comex.controller;

import br.com.alura.comex.dto.Dto;
import br.com.alura.comex.entity.Perfil;
import br.com.alura.comex.repository.PerfilRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/perfis")
public class PerfisController extends CrudController<PerfilRepository> {

    public PerfisController(PerfilRepository repository) {
        super(repository);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid RequestDto form, UriComponentsBuilder uriBuilder) {
        
        return super.insert(form, "/api/perfis/{id}", uriBuilder);
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

    public void doUpdate (Object object, Dto dto) {
        Perfil entity = (Perfil) object;
        RequestDto form = (RequestDto) dto;
        entity.setNome(form.getNome());
    }

    public static class RequestDto extends Dto<Perfil, Long> {

        private Long id;
        @Size (min = 2)
        private String nome;

        public RequestDto() {
        }

        public Perfil toEntity() {
            Perfil entity = new Perfil();
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
