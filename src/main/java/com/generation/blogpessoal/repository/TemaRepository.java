package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Tema;

public interface TemaRepository extends JpaRepository<Tema, Long> {
	
	// Busca todos os temas cuja descrição contenha o texto informado,
	// ignorando se está em maiúsculas ou minúsculas
	public List<Tema> findAllByDescricaoContainingIgnoreCase(String descricao); // SELECT * FROM tb_temas WHERE descricao LIKE '%{descricao}%';  (SQL) <- Isso é o que o método findAllByDescricaoContainingIgnoreCase() faz, ele retorna todos os registros da tabela tb_temas cujo campo descricao contenha o texto informado, ignorando se está em maiúsculas ou minúsculas

}
