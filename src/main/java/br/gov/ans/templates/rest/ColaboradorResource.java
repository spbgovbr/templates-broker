package br.gov.ans.templates.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.NotImplementedException;

import br.gov.ans.utils.MessageUtils;

@Path("colaboradores")
public class ColaboradorResource {

    @Inject
    private MessageUtils messages;
	
	@GET
	@Path("{colaborador}/templates")
	public Response getTemplates(@PathParam("colaborador") String colaborador){
		throw new NotImplementedException(messages.getMessage("erro.servico.nao.implementado"));
	}
		
}
