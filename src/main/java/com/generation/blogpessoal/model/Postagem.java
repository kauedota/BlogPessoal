package com.generation.blogpessoal.model; 


import java.time.LocalDateTime; // Importa a classe para trabalhar com data e hora

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
// Importações necessárias para trabalhar com JPA (mapeamento objeto-relacional) e validações

@Entity // Indica que esta classe é uma entidade JPA (vai virar uma tabela no banco)
@Table(name = "tb_postagens") // Define o nome da tabela no banco de dados
public class Postagem {
    
    @Id // Define o atributo como chave primária (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera valores automáticos (AUTO_INCREMENT)
    private Long id; // Coluna "id" da tabela
    
    @NotBlank(message = "O atributo título é obrigatório e não pode utilizar espaços em branco!") // Validação: não pode ser nulo nem vazio
    @Size(min = 5, max = 100, message = "O atributo título deve conter no mínimo 05 e no máximo 100 caracteres")    // Validação: tamanho mínimo e máximo
    @Column(length = 100) // Define o tamanho máximo da coluna no banco
    private String titulo; // Coluna "titulo" da tabela
    
    @NotBlank(message = "O atributo texto é obrigatório e não pode utilizar espaços em branco!") // Validação: não pode ser nulo nem vazio
    @Size(min = 5, max = 1000, message = "O atributo texto deve conter no mínimo 05 e no máximo 1000 caracteres")    // Validação: tamanho mínimo e máximo
    @Column(length = 1000) // Define o tamanho máximo da coluna no banco
    private String texto; // Coluna "texto" da tabela
    
    @UpdateTimestamp // Atualiza automaticamente a data e hora sempre que o registro for atualizado
    private LocalDateTime data; // Coluna "data" da tabela (armazena data e hora)
    
    @ManyToOne() // Define o relacionamento muitos-para-um com a entidade Tema
    @JsonIgnoreProperties("postagem") // Evita recursão infinita na serialização JSON
    private Tema tema; // Coluna "tema_id" da tabela (chave estrangeira para a tabela de temas)
    
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;

    // Métodos Getters e Setters (acessam e modificam os atributos privados)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
    

}
