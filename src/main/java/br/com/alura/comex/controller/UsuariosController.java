package br.com.alura.comex.controller;

import br.com.alura.comex.dto.Dto;
import br.com.alura.comex.entity.*;
import br.com.alura.comex.repository.ProdutoRepository;
import br.com.alura.comex.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController extends CrudController<UsuarioRepository> {

    public UsuariosController(UsuarioRepository repository) {
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

    @Override
    public Object getDetailedDto(Object entity) {
        return getDto(entity);
    }

    public record ResponseDto(
            Long id,
            String email,
            Cliente cliente,
            List<Perfil> perfis) {
        public ResponseDto(Usuario entity) {
            this(
                    entity.getId(),
                    entity.getEmail(),
                    entity.getCliente(),
                    entity.getPerfis());
        }
    }


    public static class RequestDto extends Dto<Usuario, Long> {

        private Long id;
    
        private String email;
    
        private String senha;
    
        public Usuario toEntity() {
            Usuario entity = new Usuario();
            //entity.getPerfis().add(new Perfil("usuario"));
            return toEntity(entity);
        }
        public Usuario toEntity(Usuario entity) {
            entity.setEmail(email);
            entity.setPassword(senha);
            return entity;
        }
        public UsernamePasswordAuthenticationToken toToken() {
            return new UsernamePasswordAuthenticationToken(email, senha);
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setEmail(String nome) {
            this.email = nome;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}
