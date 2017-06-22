package br.gov.ans.templates.teste;


import org.junit.BeforeClass;

import com.jayway.restassured.RestAssured;

public class FunctionalTest {

	protected final String USUARIO = "andre.guimaraes";
	protected final String SENHA = "1qaz2wsx";
	protected static final Boolean DESENVOLVIMENTO = true;
	
    @BeforeClass
    public static void setup() {
    	if(DESENVOLVIMENTO){
			String port = System.getProperty("server.port");
			if (port == null) {
				RestAssured.port = Integer.valueOf(8080);
			} else {
				RestAssured.port = Integer.valueOf(port);
			}
    	}

        String basePath = System.getProperty("server.base");
        if(basePath==null){
            basePath = "/templates-broker/service/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
        	if(DESENVOLVIMENTO){
        		baseHost = "http://localhost";        		
        	}else{
//        		baseHost = "http://ansprwww02.ans.gov.br";   
        	}
        }
        RestAssured.baseURI = baseHost;

    }
}
