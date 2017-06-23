package br.gov.ans.templates.teste;


import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.gov.ans.templates.to.Template;

import com.jayway.restassured.response.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TemplatesTest extends FunctionalTest{	
			
//	@Test
	public void AA_incluirTemplateTest(){
		Response response = given()
				.auth()
				.basic(USUARIO, SENHA)
				.contentType("application/json")
				.body(buildTemplate("gear-nota-gefap-deferimento.mustache"))
				.when().post("/templates");
		
//		processoCriado = response.getBody().as(RetornoGeracaoProcedimento.class).getProcedimentoFormatado().replaceAll("[^0-9+]", "");
				
		response.then().statusCode(201);
	}
	
	public Template buildTemplate(String arquivo){
		Template template = new Template();
		
		template.setNome("gear-nota-gefap-deferimento");
		template.setDescricao("Notas Técnicas de Deferimento antes e depois do Reajuste do sistema GEAR.");
		template.setResponsavel("cristiano.rocha");
		template.setExemplo("Não informado");
		template.setCorpo(new String(readFile(arquivo)));
		
		return template;
	}
	
	public byte[] readFile(String nome){
		ClassLoader classLoader = getClass().getClassLoader();
		
		File file = new File(classLoader.getResource(nome).getFile());
		
	    byte[] bytes = new byte[(int) file.length()];
	    
        try {
        	FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return bytes;
	}
 }
