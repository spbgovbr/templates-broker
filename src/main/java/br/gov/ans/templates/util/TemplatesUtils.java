package br.gov.ans.templates.util;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.gov.ans.exceptions.BusinessException;


public class TemplatesUtils {

	public static URI getResourcePath(UriInfo uriInfo,String resourceId){
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		
		builder.path(resourceId);
		
		return builder.build();		
	}
	
	public static Integer parseInt(String valor) throws BusinessException{
		try{
			return Integer.parseInt(valor);			
		}catch(Exception e){
			throw new BusinessException("O valor '"+valor+"' não pode ser convertido para int.");
		}		
	}
}
