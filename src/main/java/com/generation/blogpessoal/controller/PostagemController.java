package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.model.Postagem;

@RestController // Diz que essa classe é um "controlador" da API
@RequestMapping("/postagens") // Caminho da API: /postagens
@CrossOrigin(origins = "*", allowedHeaders = "*") // Permite acesso de qualquer lugar
public class PostagemController {
    
    @Autowired // Pede para o Spring criar o objeto postagemRepository
    private final PostagemRepository postagemRepository;

    PostagemController(PostagemRepository postagemRepository) {
        this.postagemRepository = postagemRepository;
    }
    
    @GetMapping // Quando alguém chama GET /postagens
    public ResponseEntity<List<Postagem>> getAll() {
        // Busca todas as postagens no banco e devolve
        return ResponseEntity.ok(postagemRepository.findAll());
		// SELECT * FROM tb_postagens;  (SQL) <- Isso é o que o método findAll() faz, ele retorna todos os registros da tabela tb_postagens
        
        
	}
}
