package br.gov.ans.templates.teste;


import org.junit.BeforeClass;

import com.jayway.restassured.RestAssured;

public class BaseTest {

	protected static final String USUARIO = "teste-rest-assured";
	protected static final String SENHA = "assured-testes";
	protected static final String HOST = "http://ansdsjboss01";
	protected static final int PORTA = 80;
		
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = HOST;
		RestAssured.port = PORTA;
        RestAssured.basePath = "/templates-broker/service/";
    }
    
}
