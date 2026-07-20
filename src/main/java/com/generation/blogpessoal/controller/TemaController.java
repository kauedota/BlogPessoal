package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController // Indica que esta classe é um controlador REST (API)
@RequestMapping("/temas") // Define o endpoint base da API como /temas
@CrossOrigin(origins = "*", allowedHeaders = "*") // Permite requisições de qualquer origem
public class TemaController {
    
    @Autowired // Injeta automaticamente o TemaRepository
    private TemaRepository temaRepository;
    
    @GetMapping // GET /temas → lista todos os temas
    public ResponseEntity<List<Tema>> getAll(){
        return ResponseEntity.ok(temaRepository.findAll());
    }
    
    @GetMapping("/{id}") // GET /temas/{id} → busca tema pelo ID
    public ResponseEntity<Tema> getById(@PathVariable Long id){
        return temaRepository.findById(id)
            .map(resposta -> ResponseEntity.ok(resposta)) // Se encontrar, retorna 200 OK
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Se não encontrar, retorna 404
    }
    
    @GetMapping("/descricao/{descricao}") // GET /temas/descricao/{descricao} → busca por descrição
    public ResponseEntity<List<Tema>> getAllByDescricao(@PathVariable String descricao){
        return ResponseEntity.ok(
            temaRepository.findAllByDescricaoContainingIgnoreCase(descricao)
        );
    }
    
    @PostMapping // POST /temas → cria um novo tema
    public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema){
        
        tema.setId(null); // Garante que o ID seja gerado automaticamente
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(temaRepository.save(tema)); // Salva e retorna o tema criado
    }
    
    @PutMapping // PUT /temas → atualiza um tema existente
    public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema){
        return temaRepository.findById(tema.getId())
            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
            .body(temaRepository.save(tema))) // Se existir, atualiza e retorna
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Se não existir, retorna 404
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna 204 se deletar com sucesso
    @DeleteMapping("/{id}") // DELETE /temas/{id} → deleta tema pelo ID
    public void delete(@PathVariable Long id) {
        Optional<Tema> tema = temaRepository.findById(id);
        
        if(tema.isEmpty()) // Se não encontrar, lança erro 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        
        temaRepository.deleteById(id); // Se encontrar, deleta o tema
    }

}
