package br.gov.ans.templates.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("colaboradores")
public class ColaboradorResource {

	@GET
	@Path("{colaborador}/templates")
	public Response getTemplates(@PathParam("colaborador") String colaborador){
		return null;
	}
		
}
