package br.com.alura.comex.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCategoria status = StatusCategoria.ATIVA;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos = new ArrayList<>();

    public Categoria() {
    }
    public Categoria(Long id) {
        this.id = id;
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

    public StatusCategoria getStatus() {
        return status;
    }

    public void setStatus(StatusCategoria status) {
        this.status = status;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void adicionaProduto(Produto produto){
        produto.setCategoria(this);
        this.produtos.add(produto);
    }

}