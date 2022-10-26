package br.com.alura.comex.controller;

import javax.validation.Valid;

import br.com.alura.comex.entity.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.comex.dto.Dto;
import br.com.alura.comex.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController extends CrudController<ClienteRepository> {

    public ClientesController(ClienteRepository repository) {
        super(repository);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid RequestDto form, UriComponentsBuilder uriBuilder) {
        return super.insert(form, "/api/clientes/{id}", uriBuilder);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid RequestDto form) {
        return super.update(id, form);
    }
    public void doUpdate (Object object, Dto dto) {
        Cliente entity = (Cliente) object;
        RequestDto form = (RequestDto) dto;
        form.toEntity(entity);
    }

    public static class RequestDto extends Dto<Cliente, Long> {

        private Long id;
    
        private String nome;
        private String cpf;
        private String telefone;
        private String rua;
        private String numero;
        private String complemento;
        private String bairro;
        private String cidade;
        private String estado;
    
        public Cliente toEntity() {
            Cliente entity = new Cliente();
            return toEntity(entity);
        }
        public Cliente toEntity(Cliente entity) {
            entity.setNome(nome);
            entity.setCpf(cpf);
            entity.setTelefone(telefone);
            entity.setRua(rua);
            entity.setNumero(numero);
            entity.setComplemento(complemento);
            entity.setBairro(bairro);
            entity.setCidade(cidade);
            entity.setEstado(estado);
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

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getRua() {
            return rua;
        }

        public void setRua(String rua) {
            this.rua = rua;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getComplemento() {
            return complemento;
        }

        public void setComplemento(String complemento) {
            this.complemento = complemento;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

    }
}
