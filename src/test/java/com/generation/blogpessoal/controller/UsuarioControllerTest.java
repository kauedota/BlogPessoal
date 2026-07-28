package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;
import com.generation.blogpessoal.util.JwtHelper;
import com.generation.blogpessoal.util.TestBuilder;

@AutoConfigureTestRestTemplate // Configura o TestRestTemplate para ser usado nos testes, permitindo a simulação de requ
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Inicia o contexto da aplicação para testes, permitindo que os testes sejam executados em um ambiente simulado.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Define o ciclo de vida dos testes para que uma única instância da classe de teste seja usada para todos os métodos de teste.
@TestMethodOrder(MethodOrderer.DisplayName.class) // Define a ordem de execução dos métodos de teste com base nos nomes dos métodos.
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate; // Injeção do TestRestTemplate para simular requisições HTTP nos testes.
	
	@Autowired
	private UsuarioService usuarioService; // Injeção do UsuarioService para interagir com a camada de serviço da aplicação durante os testes.
	
	@Autowired
	private UsuarioRepository usuarioRepository; // Injeção do UsuarioRepository para interagir com a camada
	
	private static final String BASE_URL = "/usuarios"; // Define a URL base para os endpoints de usuário, facilitando a construção das requisições nos testes.
	private static final String USUARIO = "root@root.com"; // Define um usuário padrão para os testes, que será usado para autenticação e validação de operações relacionadas a usuários.
	private static final String SENHA = "rootroot"; // Define a senha
	
	@BeforeAll // Anotação que indica que o método deve ser executado antes de todos os testes, garantindo que o estado inicial seja configurado corretamente.
	void inicio() {
		usuarioRepository.deleteAll(); // Limpa o repositório de usuários antes de cada teste, garantindo que os testes sejam executados em um estado limpo e consistente.
		usuarioService.cadastrarUsuario(TestBuilder.criarUsuario(null, "Root", USUARIO, SENHA)); // Cadastra um usuário padrão no repositório antes de cada teste, garantindo que haja um usuário válido para autenticação e validação de operações relacionadas a usuários.
	}
	
	@Test // Anotação que indica que o método é um teste a ser executado pelo framework de testes.
	@DisplayName("01 - Deve Cadastrar um novo Usuário com sucesso") // Define o nome do teste, que será exibido nos relatórios de execução dos testes, facilitando a identificação do propósito do teste.
	void deveCadastrarUsuario() { // Implementação do teste para cadastrar um novo usuário com sucesso
		// Given - Dado que
		Usuario usuario = TestBuilder.criarUsuario(null, "Paulo Antunes", "pauloantunes@email.com.br", "paulo123"); // Cria um objeto Usuario com os dados do novo usuário a ser cadastrado.
		
		// When - Quando
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuario); // Cria uma requisição HTTP com o objeto Usuario como corpo da requisição.
		
		// Enviar a requisição POST para o endpoint de cadastro de usuários e obter a resposta
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange(BASE_URL + "/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class); // Envia a requisição POST para o endpoint de cadastro de usuários e obtém a resposta.
			
		// Then - Então
		
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode()); // Verifica se o status da resposta é 200 OK, indicando que o cadastro foi bem-sucedido.
		assertNotNull(resposta.getBody()); // Verifica se o corpo da resposta não é nulo, garantindo que um objeto Usuario foi retornado.
		
		}
		
		@Test
		@DisplayName("02 - Não deve permitir duplicação do Usuário")
		void naoDeveCadastrarUsuarioDuplicado() {
			// Given - Dado que
			Usuario usuario = TestBuilder.criarUsuario(null, "Maria da Silva", "marialuiza@email.com.br", "maria123");

			// When - Quando

			// Primeiro cadastro - deve funcionar normalmente
			HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuario);
			testRestTemplate.exchange(BASE_URL + "/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

			// Segunda tentativa de cadastro com o MESMO e-mail - deve ser bloqueada
			ResponseEntity<Usuario> resposta = testRestTemplate.exchange(BASE_URL + "/cadastrar", HttpMethod.POST,
					corpoRequisicao, Usuario.class);

			// Then - Então
			assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
			assertNull(resposta.getBody());
		}
		
		@Test
		@DisplayName("03 - Deve listar todos os Usuário")
		void deveListarTodosUsuarios() {
			// Given - Dado que
			usuarioService.cadastrarUsuario(TestBuilder.criarUsuario(null, "Sabrina Sanches", "sabrina@email.com.br", "sabrina123"));
			usuarioService.cadastrarUsuario(TestBuilder.criarUsuario(null, "Ricardo Marques", "ricardo@email.com.br", "ricardo123"));
			
			// When - Quando
			
			String token = JwtHelper.obterToken(testRestTemplate, USUARIO, SENHA); // Obtém um token JWT válido para autenticação usando o usuário e senha definidos anteriormente.
			
			// Cabeçalho da Requisição
			HttpEntity<Void> cabeçalhoRequisicao = JwtHelper.criarRequisicaoComToken(token); // Cria uma requisição HTTP com o token JWT no cabeçalho para autenticação.
			
			// Enviar a requisição GET para o endpoint de listagem de usuários e obter a resposta
			ResponseEntity<Usuario[]> resposta = testRestTemplate.exchange(BASE_URL + "/all", HttpMethod.GET, cabeçalhoRequisicao, Usuario[].class); // Envia a requisição GET para o endpoint de listagem de usuários e obtém a resposta.
			
			// Then - Então
			
			assertEquals(HttpStatus.OK, resposta.getStatusCode()); // Verifica se o status da resposta é 200 OK, indicando que a listagem foi bem-sucedida.
			assertNotNull(resposta.getBody()); // Verifica se o corpo da resposta não é nulo, garantindo que uma lista de usuários foi retornada. 
			
		}
		
		@Test
		@DisplayName("04 - Deve Atualizar os dados do usuário com sucesso")
		void deveAtualizarUsuario() {
			// Given

			// Objeto para fazer o cadastro
			Usuario usuario = TestBuilder.criarUsuario(null, "Daniel", "daniel@email.com.br", "daniel1234");

			// Fiz o cadastro e guardei os dados objeto
			Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);

			// Preparar o objeto com a atualização
			Usuario usuarioUpdate = TestBuilder.criarUsuario(usuarioCadastrado.get().getId(), "Daniel Araujo",
					"daniel_araujo@email.com.br", "abcd1234");

			// When

			// Obter o Token
			String token = JwtHelper.obterToken(testRestTemplate, USUARIO, SENHA);

			// Cabeçalho da Requisição
			HttpEntity<Usuario> cabecalhoRequisicao = JwtHelper.criarRequisicaoComToken(usuarioUpdate, token);

			// Enviar a Requisição
			ResponseEntity<Usuario> resposta = testRestTemplate.exchange(BASE_URL + "/atualizar", HttpMethod.PUT,
					cabecalhoRequisicao, Usuario.class);

			// Then

			assertEquals(HttpStatus.OK, resposta.getStatusCode());
			assertNotNull(resposta.getBody());

		}
		
		@Test
		@DisplayName("05 - Deve deletar um usuário com sucesso")
		void deveDeletarUsuario() {
			// Given
			Usuario usuario = TestBuilder.criarUsuario(null, "Carla", "carla@email.com.br", "carla1234");
			Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);

			// When
			String token = JwtHelper.obterToken(testRestTemplate, USUARIO, SENHA);
			HttpEntity<Void> cabecalhoRequisicao = JwtHelper.criarRequisicaoComToken(token);

			// Enviar a requisição DELETE para o endpoint
			ResponseEntity<Void> resposta = testRestTemplate.exchange(BASE_URL + "/" + usuarioCadastrado.get().getId(), // endpoint
																														// de
																														// delete
					HttpMethod.DELETE, cabecalhoRequisicao, Void.class);

			// Then
			assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode()); // 204 esperado
			assertFalse(usuarioRepository.findById(usuarioCadastrado.get().getId()).isPresent()); // garante que foi
																									// removido
		}

}
