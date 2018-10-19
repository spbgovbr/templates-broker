package br.gov.ans.templates.rest;

import static br.gov.ans.templates.util.TemplatesUtils.getResourcePath;
import static br.gov.ans.templates.util.TemplatesUtils.parseInt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.gov.ans.dao.DAO;
import br.gov.ans.exceptions.BusinessException;
import br.gov.ans.exceptions.ResourceConflictException;
import br.gov.ans.exceptions.ResourceNotFoundException;
import br.gov.ans.exceptions.WrappedException;
import br.gov.ans.templates.dao.ColaboradorDAO;
import br.gov.ans.templates.dao.TemplatesDAO;
import br.gov.ans.templates.modelo.Colaborador;
import br.gov.ans.templates.modelo.Template;
import br.gov.ans.templates.modelo.Versao;
import br.gov.ans.utils.MessageUtils;

@Path("/templates")
@Stateless
public class TemplateResource {
	
	@Inject
	private TemplatesDAO daoTemplate;
	
	@Inject
	private DAO<Versao> daoVersao;
	
	@Inject
	private ColaboradorDAO daoColaborador;
	
	@Context
	private UriInfo uriInfo;
	
    @Inject
    private MessageUtils messages;

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response listTemplates(@QueryParam("filtro") String filtro, @QueryParam("pag") String pagina, @QueryParam("itens") String tamanhoPagina) throws BusinessException, ResourceNotFoundException{
		List<Template> templates = daoTemplate.getTemplates(filtro, pagina == null? null:parseInt(pagina), tamanhoPagina == null? null : parseInt(tamanhoPagina));
		
		if(templates.isEmpty()){
			throw new ResourceNotFoundException(messages.getMessage("templates.nao.encontrados.filtro",filtro));
		}
		
		GenericEntity<List<Template>> entity = new GenericEntity<List<Template>>(templates){};
		
		Long totalRegistros = daoTemplate.countTemplates(filtro);
				
		return Response.ok().entity(entity).header("total_registros", totalRegistros).build(); 
	}
	
	@GET
	@Path("{template}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Template getTemplate(@PathParam("template") String nomeTemplate) throws ResourceNotFoundException{
		Template template = daoTemplate.getTemplate(nomeTemplate);
		
		if(template == null){
			throw new ResourceNotFoundException(messages.getMessage("erro.template.nao.encontrado", nomeTemplate));			
		}
				
		return template;
	}

	@GET
	@Path("{template}/corpo")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String getCorpoTemplate(@PathParam("template") String nomeTemplate) throws ResourceNotFoundException{
		return new String(getTemplate(nomeTemplate).getCorpo());
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response createTemplate(@Valid br.gov.ans.templates.to.Template template) throws WrappedException, ResourceNotFoundException, ResourceConflictException{
		Template entity = new Template(template);
		
		if(daoTemplate.getTemplate(template.getNome()) != null){
			throw new ResourceConflictException(messages.getMessage("erro.template.existente",template.getNome()));
		}
		
		try{
			daoTemplate.persist(entity);
			adicionarColaboradorCriador(entity, template.getResponsavel());
		}catch(Exception ex){
			throw new WrappedException(ex);
		}
		
		return Response.created(getResourcePath(uriInfo, entity.getNome())).build();
	}

	@PUT
	@Path("{template}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response editTemplate(@PathParam("template") String nomeTemplate, br.gov.ans.templates.to.Template template) throws ResourceNotFoundException{
		Template templateAtual = getTemplate(nomeTemplate);
		
		if(templateAtual.getRestrito()){
			verificaPermissaoColaborador(templateAtual.getId(),template.getResponsavel());			
		}else{
			registrarColaborador(templateAtual.getId(),template.getResponsavel());
		}
		
		registrarHistoricoVersao(templateAtual);
		
		templateAtual.setNome(template.getNome());
		templateAtual.setDescricao(template.getDescricao());
		templateAtual.setCorpo(template.getCorpo().getBytes());
		templateAtual.setExemplo(template.getExemplo());
		templateAtual.setResponsavel(template.getResponsavel());
		templateAtual.setRestrito(template.getRestrito());
		templateAtual.setDataCadastro(new Date());
		
		daoTemplate.merge(templateAtual);
		
		return Response.ok().contentLocation(getResourcePath(uriInfo, template.getNome())).build();
	}

	@DELETE
	@Path("{template}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response deleteTemplate(@PathParam("template") String nomeTemplate) throws ResourceNotFoundException{
		Template template = getTemplate(nomeTemplate);
		
		template.setDataExclusao(new Date());
		
		daoTemplate.merge(template);
		
		return Response.ok().build();
	}

	@GET
	@Path("{template}/colaboradores")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getColaboradores(@PathParam("template") String template, @QueryParam("pag") String pagina, @QueryParam("itens") String tamanhoPagina) throws ResourceNotFoundException, BusinessException{
		Long idTemplate = getTemplate(template).getId();
		
		ArrayList<Colaborador> colaboradores = (ArrayList<Colaborador>) daoColaborador.getColaboradores(
				idTemplate, pagina == null ? null : parseInt(pagina),
				tamanhoPagina == null ? null : parseInt(tamanhoPagina));

		GenericEntity<List<Colaborador>> entity = new GenericEntity<List<Colaborador>>(colaboradores){};
		
		Long totalRegistros = daoColaborador.countColaboradores(idTemplate);
		
		return Response.ok().entity(entity).header("total_registros", totalRegistros).build();
	}
	
	@GET
	@Path("{template}/colaboradores/{colaborador}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Colaborador getColaborador(@PathParam("template") String template, @PathParam("colaborador") String colaborador) throws ResourceNotFoundException{
		return daoColaborador.getColaborador(getTemplate(template).getId(), colaborador);
	}

	@POST
	@Path("{template}/colaboradores")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response addColaborador(@PathParam("template") String nomeTemplate, Colaborador colaborador) throws ResourceNotFoundException{
		Template template = getTemplate(nomeTemplate);
		
		colaborador.setIdTemplate(template.getId());
		
		daoColaborador.merge(colaborador);
		
		return Response.ok().contentLocation(getResourcePath(uriInfo, colaborador.getUsuario())).build();
	}

	@DELETE
	@Path("{template}/colaboradores/{colaborador}")
	public Response removeColaborador(@PathParam("template") String template,@PathParam("colaborador") String nomeColaborador) throws ResourceNotFoundException{
		Colaborador colaborador = getColaborador(template, nomeColaborador);
		
		colaborador.setDataExclusao(new Date());
		
		daoColaborador.merge(colaborador);
		
		return Response.ok().build();
	}

	@GET
	@Path("{template}/versoes/")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getVersoes(@PathParam("template") String nomeTemplate) throws ResourceNotFoundException{
		Template template = getTemplate(nomeTemplate);
		
		HashMap<String, Object> params = new HashMap<String, Object>();		
		params.put("template", template.getId());
		
		List<Versao> versoes = daoVersao.executeNamedQuery("Versao.recuperarVersoes", params);
		
		GenericEntity<List<Versao>> entity = new GenericEntity<List<Versao>>(versoes){};
		
		return Response.ok().entity(entity).build();
	}
	
	@DELETE
	@Path("excluidos/{template}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response undeleteTemplate(@PathParam("template") String nomeTemplate) throws ResourceNotFoundException{
		Template template = getTemplateExcluido(nomeTemplate);
		
		template.setDataExclusao(null);
		
		daoTemplate.merge(template);
		
		return Response.ok().build();
	}

	@GET
	@Path("excluidos")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response listTemplatesExcluidos(@QueryParam("filtro") String filtro, @QueryParam("pag") String pagina, @QueryParam("itens") String tamanhoPagina) throws BusinessException, ResourceNotFoundException{
		List<Template> templates = daoTemplate.getTemplatesExcluidos(filtro, pagina == null? null:parseInt(pagina), tamanhoPagina == null? null : parseInt(tamanhoPagina));
		
		if(templates.isEmpty()){
			throw new ResourceNotFoundException(messages.getMessage("templates.nao.encontrados.filtro",filtro));
		}
		
		GenericEntity<List<Template>> entity = new GenericEntity<List<Template>>(templates){};
		
		Long totalRegistros = daoTemplate.countTemplatesExcluidos(filtro);
				
		return Response.ok().entity(entity).header("total_registros", totalRegistros).build(); 
	}
	
	public Template getTemplateExcluido(String nomeTemplate) throws ResourceNotFoundException{
		Template template = daoTemplate.getTemplateExcluido(nomeTemplate);
		
		if(template == null){
			throw new ResourceNotFoundException(messages.getMessage("erro.template.nao.encontrado", nomeTemplate));			
		}
		
		return template;
	}
	
	public void registrarHistoricoVersao(Template template){
		Versao versao = new Versao(template);
		
		daoVersao.persist(versao);
	}
	
	public void adicionarColaboradorCriador(Template template, String usuario){
		Colaborador colaborador = new Colaborador();
		
		colaborador.setUsuario(usuario);
		colaborador.setEditor(true);
		colaborador.setIdTemplate(template.getId());
		
		daoColaborador.persist(colaborador);
	}
	
	public void verificaPermissaoColaborador(Long idTemplate, String colaborador) throws ResourceNotFoundException{
		daoColaborador.getColaborador(idTemplate, colaborador);
	}
	
	public void registrarColaborador(Long idTemplate, String usuario){
		Colaborador colaborador = new Colaborador();
		
		colaborador.setUsuario(usuario);
		colaborador.setEditor(true);
		colaborador.setIdTemplate(idTemplate);
		
		daoColaborador.merge(colaborador);
	}
}
