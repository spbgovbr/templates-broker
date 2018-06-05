package br.gov.ans.templates.teste;


import static com.jayway.restassured.RestAssured.given;

import java.time.LocalDateTime;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.gov.ans.templates.to.Template;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TemplateTest extends BaseTest{	
			
	private static final String nomeTemplate = "teste-auto-" + LocalDateTime.now();
	
	@Test
	public void A_deveIncluirTemplate(){
		Template template = new Template();
		
		template.setNome(nomeTemplate);
		template.setDescricao("Teste automatizado realizado pelo REST Assured.");
		template.setResponsavel("teste-rest-assured");
		template.setExemplo("Não há");
		template.setCorpo("<html><body><h1>TESTE AUTOMATIZADO!</h1></body></html>");
		
		given()
			.auth()
			.basic(USUARIO, SENHA)
			.contentType("application/json")
			.body(template)
		.expect()
			.statusCode(201)
		.when()
			.post("/templates");
	}

	@Test
	public void B_deveListarTemplates(){
		given()
			.auth()
			.basic(USUARIO, SENHA)
		.expect()
			.statusCode(200)
		.when()
			.get("/templates");
	}
	
	@Test
	public void C_deveConsultarTemplate(){
		given()
			.auth()
			.basic(USUARIO, SENHA)
		.expect()
			.statusCode(200)
		.when()
			.get("/templates/" + nomeTemplate);
	}
	
	@Test
	public void D_deveRetornarCorpoTemplate(){
		given()
			.auth()
			.basic(USUARIO, SENHA)
		.expect()
			.statusCode(200)
		.when()
			.get("/templates/" + nomeTemplate + "/corpo");
	}
	
	@Test
	public void E_deveAtualizarTemplate(){
		Template template = new Template();
		
		template.setNome(nomeTemplate);
		template.setDescricao("Teste automatizado de edição realizado pelo REST Assured.");
		template.setResponsavel("teste-rest-assured");
		template.setExemplo("Não há");
		template.setCorpo("<html><body><h1>TESTE AUTOMATIZADO DE EDIÇÃO!</h1></body></html>");
		
		given()
			.auth()
			.basic(USUARIO, SENHA)
			.contentType("application/json")
			.body(template)
		.expect()
			.statusCode(200)
		.when()
			.put("/templates/" + nomeTemplate);
	}	
	
	@Test
	public void F_deveListarVersoesTemplate(){
		given()
			.auth()
			.basic(USUARIO, SENHA)
		.expect()
			.statusCode(200)
		.when()
			.get("/templates/" + nomeTemplate + "/versoes");
	}
	
	@Test
	public void G_deveExcluirTemplate(){		
		given()
			.auth()
			.basic(USUARIO, SENHA)
		.expect()
			.statusCode(200)
		.when()
			.delete("/templates/" + nomeTemplate);		
	}
	
 }
