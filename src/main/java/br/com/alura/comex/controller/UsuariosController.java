package br.com.alura.comex.controller;

import br.com.alura.comex.dto.Dto;
import br.com.alura.comex.entity.Categoria;
import br.com.alura.comex.entity.Produto;
import br.com.alura.comex.entity.Usuario;
import br.com.alura.comex.repository.ProdutoRepository;
import br.com.alura.comex.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController extends CrudController<UsuarioRepository> {

    public UsuariosController(UsuarioRepository repository) {
        super(repository);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid RequestDto form, UriComponentsBuilder uriBuilder) {
        return super.insert(form, "/api/usuarios/{id}", uriBuilder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid RequestDto form) {
        return super.update(id, form);
    }
    
    public void doUpdate (Object object, Dto dto) {
        Usuario entity = (Usuario) object;
        RequestDto form = (RequestDto) dto;
        form.toEntity(entity);
    }

    @Override
    public Object getDto(Object entity) {
        return new ResponseDto((Usuario) entity);
    }

    public record ResponseDto(
            Long id,
            String nome) {
        public ResponseDto(Usuario entity) {
            this(
                    entity.getId(),
                    entity.getNome());
        }
    }


    public static class RequestDto extends Dto<Usuario, Long> {

        private Long id;
    
        private String nome;
    
        private String senha;
    
        public Usuario toEntity() {
            Usuario entity = new Usuario();
            return toEntity(entity);
        }
        public Usuario toEntity(Usuario entity) {
            entity.setNome(nome);
            entity.setPassword(senha);
            return entity;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}