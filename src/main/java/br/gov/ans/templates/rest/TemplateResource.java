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
	
	/**
	 * @api {get} /templates Listar templates
	 * @apiName listTemplates
	 * @apiGroup Template
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE, RO_USUARIO_TEMPLATE
	 *
	 * @apiDescription Consulta os templates disponíveis.
	 * 
	 * @apiParam (Query Parameters) {String} [filtro] Valor utilizado para filtrar os templates.
	 * @apiParam (Query Parameters) {String} [itens = 20] Quantidade de templates que serão exibidos
	 * @apiParam (Query Parameters) {String} [pag = 1] Número da página
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/templates-broker/service/templates
	 *
	 * @apiSuccess (Sucesso - 200) {List} resultado Lista com os templates encontrados.
	 * @apiSuccess (Sucesso - 200) {Template} resultado.template Objeto representando o template.
	 * @apiSuccess (Sucesso - 200) {String} resultado.template.corpo Corpo do template, conteúdo que as aplicações usarão para preencher e exibir os dados.
	 * @apiSuccess (Sucesso - 200) {String} resultado.template.dataCadastro Data de cadastro do template.
	 * @apiSuccess (Sucesso - 200) {String} resultado.template.descricao Descrição do template.
	 * @apiSuccess (Sucesso - 200) {String} resultado.template.exemplo Exemplo de request para preenchimento do template.
	 * @apiSuccess (Sucesso - 200) {String} resultado.template.nome Identificador do template
	 * @apiSuccess (Sucesso - 200) {String} resultado.template.responsavel Analista responsável pelo template.
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado.template.restrito Flag identificando se a atualização deste template é restrita.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "corpo": "Prezado {{fulano}}, seu cadastro foi realizado com sucesso.",
	 *       "dataCadastro": "2017-03-14T16:57:47.405-03:00",
	 *       "descricao": "Template de confirmação de cadastro.",
	 *       "exemplo": "{"fulano":"André Guimarães"}",
	 *       "nome": "confirmacao-cadastro",
	 *       "responsavel": "andre.guimaraes",
	 *       "restrito": false
	 *     }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
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
	
	/**
	 * @api {get} /templates/:template Consultar template
	 * @apiName getTemplate
	 * @apiGroup Template
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE, RO_USUARIO_TEMPLATE
	 *
	 * @apiDescription Consulta um template.
	 * 
	 * @apiParam (Path Parameters) {String} template Identificador do template
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/templates-broker/service/templates/confirmacao-cadastro
	 *
	 * @apiSuccess (Sucesso - 200) {Template} template Objeto representando o template.
	 * @apiSuccess (Sucesso - 200) {String} template.corpo Corpo do template, conteúdo que as aplicações usarão para preencher e exibir os dados.
	 * @apiSuccess (Sucesso - 200) {String} template.dataCadastro Data de cadastro do template.
	 * @apiSuccess (Sucesso - 200) {String} template.descricao Descrição do template.
	 * @apiSuccess (Sucesso - 200) {String} template.exemplo Exemplo de request para preenchimento do template.
	 * @apiSuccess (Sucesso - 200) {String} template.nome Identificador do template.
	 * @apiSuccess (Sucesso - 200) {String} template.responsavel Analista responsável pelo template.
	 * @apiSuccess (Sucesso - 200) {Boolean} template.restrito Flag identificando se a atualização deste template é restrita.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "corpo": "Prezado {{fulano}}, seu cadastro foi realizado com sucesso.",
	 *       "dataCadastro": "2017-03-14T16:57:47.405-03:00",
	 *       "descricao": "Template de confirmação de cadastro.",
	 *       "exemplo": "{"fulano":"André Guimarães"}",
	 *       "nome": "confirmacao-cadastro",
	 *       "responsavel": "andre.guimaraes",
	 *       "restrito": false,
	 *     }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
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
	
	/**
	 * @api {get} /templates/:template/corpo Recuperar corpo
	 * @apiName getCorpoTemplate
	 * @apiGroup Template
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE, RO_USUARIO_TEMPLATE
	 *
	 * @apiDescription Recupera o corpo de um template para utilização.
	 * 
	 * @apiParam (Path Parameters) {String} template Identificador do template
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/templates-broker/service/templates/confirmacao-cadastro
	 *
	 * @apiSuccess (Sucesso - 200) {String} corpo String com o corpo do template pronto para utilização. 
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     Prezado {{fulano}}, seu cadastro foi realizado com sucesso.
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{template}/corpo")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String getCorpoTemplate(@PathParam("template") String nomeTemplate) throws ResourceNotFoundException{
		return new String(getTemplate(nomeTemplate).getCorpo());
	}
	
	/** 
	 * @api {post} /templates Adicionar template
	 * @apiName createTemplate
	 * @apiGroup Template
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE
	 *
	 * @apiDescription Cria um novo template.
	 * 	
	 * @apiParam (Request Body) {Template} template Objeto de representação do template.
	 * @apiParam (Request Body) {String} corpo Corpo do template, conteúdo que as aplicações usarão para preencher e exibir os dados.
	 * @apiParam (Request Body) {String} descricao Descrição do template.
	 * @apiParam (Request Body) {String} exemplo Exemplo de request para preenchimento do template.
	 * @apiParam (Request Body) {String} nome Identificador do template.
	 * @apiParam (Request Body) {String} responsavel Analista responsável pelo template.
	 * @apiParam (Request Body) {String} restrito Flag identificando se a atualização deste template é restrita.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [PUT] http://<host>/templates-broker/service/templates/confirmacao-cadastro
	 *
	 *	body:
	 *     {
	 *       "corpo": "Prezado {{fulano}}, seu cadastro foi realizado com sucesso.",
	 *       "descricao": "Template de confirmação de cadastro.",
	 *       "exemplo": "{"fulano":"André Guimarães"}",
	 *       "nome": "confirmacao-cadastro",
	 *       "responsavel": "andre.guimaraes",
	 *       "restrito": true,
	 *     }
	 *
	 * @apiSuccess (Sucesso - 201) {header} Location Caminho para o recurso criado.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 201 Created
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
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
	
	/**
	 * @api {put} /templates/:template Atualizar template
	 * @apiName editTemplate
	 * @apiGroup Template
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE
	 *
	 * @apiDescription Atualiza um template.
	 * 
	 * @apiParam (Path Parameters) {String} template Identificador do template
	 * 	
	 * @apiParam (Request Body) {Template} template Objeto de representação do template.
	 * @apiParam (Request Body) {String} corpo Corpo do template, conteúdo que as aplicações usarão para preencher e exibir os dados.
	 * @apiParam (Request Body) {String} descricao Descrição do template.
	 * @apiParam (Request Body) {String} exemplo Exemplo de request para preenchimento do template.
	 * @apiParam (Request Body) {String} nome Identificador do template.
	 * @apiParam (Request Body) {String} responsavel Analista responsável pelo template.
	 * @apiParam (Request Body) {String} restrito Flag identificando se a atualização deste template é restrita.
	 * 
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [PUT] http://<host>/templates-broker/service/templates/confirmacao-cadastro
	 *
	 *	body:
	 *     {
	 *       "corpo": "Prezado {{fulano}}, seu cadastro foi realizado com sucesso.",
	 *       "descricao": "Template de confirmação de cadastro.",
	 *       "exemplo": "{"fulano":"André Guimarães"}",
	 *       "nome": "confirmacao-cadastro",
	 *       "responsavel": "andre.guimaraes",
	 *       "restrito": true,
	 *     }
	 * 
	 * 
	 * @apiSuccess (Sucesso - 200) {header} Location Caminho para o recurso editado.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
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
	
	/**
	 * @api {delete} /templates/:template Excluir template
	 * @apiName deleteTemplate
	 * @apiGroup Template
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE
	 *
	 * @apiDescription Exclui determinado template.
	 * 
	 * @apiParam (Path Parameters) {String} template Identificador do template
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://<host>/templates-broker/service/templates/confirmacao-cadastro
	 *
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@DELETE
	@Path("{template}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response deleteTemplate(@PathParam("template") String nomeTemplate) throws ResourceNotFoundException{
		Template template = getTemplate(nomeTemplate);
		
		template.setDataExclusao(new Date());
		
		daoTemplate.merge(template);
		
		return Response.ok().build();
	}
	
	/**
	 * @api {get} /templates/:template/colaboradores Listar colaboradores
	 * @apiName getColaboradores
	 * @apiGroup Colaborador
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE, RO_USUARIO_TEMPLATE
	 *
	 * @apiDescription Consulta os colaboradores do template.
	 * 
	 * @apiParam (Path Parameters) {String} template Identificador do template
	 * 
	 * @apiParam (Query Parameters) {String} [itens = 20] Quantidade de templates que serão exibidos
	 * @apiParam (Query Parameters) {String} [pag = 1] Número da página
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/templates-broker/service/templates/confirmacao-cadastro/colaboradores
	 *
	 * @apiSuccess (Sucesso - 200) {List} resultado Lista com os colaboradores do template.
	 * @apiSuccess (Sucesso - 200) {Colaborador} resultado.colaborador Objeto representando um colaborador.
	 * @apiSuccess (Sucesso - 200) {String} resultado.colaborador.usuario Identificador do colaborador.
	 * @apiSuccess (Sucesso - 200) {Boolean} resultado.colaborador.editor Flag que determina se o colaborador tem o poder para editar o template.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "editor": true,
	 *       "usuario": "andre.guimaraes",
	 *     }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
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
	
	/**
	 * @api {get} /templates/:template/colaboradores/:colaborador Consultar colaborador
	 * @apiName getColaborador
	 * @apiGroup Colaborador
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE, RO_USUARIO_TEMPLATE
	 *
	 * @apiDescription Consulta um colaborador.
	 * 
	 * @apiParam (Path Parameters) {String} template Identificador do template
	 * @apiParam (Path Parameters) {String} colaborador Identificador do colaborador
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/templates-broker/service/templates/confirmacao-cadastro/colaboradores/andre.guimaraes
	 *
	 * @apiSuccess (Sucesso - 200) {Colaborador} colaborador Objeto representando um colaborador.
	 * @apiSuccess (Sucesso - 200) {String} colaborador.usuario Identificador do colaborador.
	 * @apiSuccess (Sucesso - 200) {Boolean} colaborador.editor Flag que determina se o colaborador tem o poder para editar o template.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "editor": true,
	 *       "usuario": "andre.guimaraes"
	 *     }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@GET
	@Path("{template}/colaboradores/{colaborador}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Colaborador getColaborador(@PathParam("template") String template, @PathParam("colaborador") String colaborador) throws ResourceNotFoundException{
		return daoColaborador.getColaborador(getTemplate(template).getId(), colaborador);
	}
	
	/**
	 * @api {post} /templates/:template/colaboradores Adicionar colaborador
	 * @apiName addColaborador
	 * @apiGroup Colaborador
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE
	 *
	 * @apiDescription Adiciona um colaborador ao template.
	 * 
	 * @apiParam (Path Parameters) {String} template Identificador do template
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	endpoint: [POST] http://<host>/templates-broker/service/templates/confirmacao-cadastro/colaboradores
	 *
	 *	body:
	 *     {
	 *       "usuario": "andre.guimaraes",
	 *       "editor": true
	 *     }
	 *
	 * @apiSuccess (Sucesso - 201) {header} Location Caminho para o recurso criado.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 201 Created
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
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
	
	/**
	 * @api {delete} /templates/:template/colaboradores/:colaborador Remover colaborador
	 * @apiName removeColaborador
	 * @apiGroup Colaborador
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE
	 *
	 * @apiDescription Remove colaborador de um template.
	 * 
	 * @apiParam (Path Parameters) {String} template Identificador do template
	 * @apiParam (Path Parameters) {String} colaborador Identificador do colaborador
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://<host>/templates-broker/service/templates/confirmacao-cadastro/colaboradores/andre.guimaraes
	 *
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@DELETE
	@Path("{template}/colaboradores/{colaborador}")
	public Response removeColaborador(@PathParam("template") String template,@PathParam("colaborador") String nomeColaborador) throws ResourceNotFoundException{
		Colaborador colaborador = getColaborador(template, nomeColaborador);
		
		colaborador.setDataExclusao(new Date());
		
		daoColaborador.merge(colaborador);
		
		return Response.ok().build();
	}
	
	/**
	 * @api {get} /templates/:template/versoes Listar versões
	 * @apiName getVersoes
	 * @apiGroup Template
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE, RO_ADMIN_TEMPLATE
	 *
	 * @apiDescription Lista as versões de um template
	 * 
	 * @apiParam (Path Parameters) {String} template Identificador do template
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -i http://<host>/templates-broker/service/templates/confirmacao-cadastro/versoes
	 *
	 * @apiSuccess (Sucesso - 200) {List} resultado Lista com as versões do template.
	 * @apiSuccess (Sucesso - 200) {Versao} resultado.versao Objeto representando uma versão do template.
	 * @apiSuccess (Sucesso - 200) {String} resultado.versao.corpo Corpo do template, conteúdo que as aplicações usarão para preencher e exibir os dados.
	 * @apiSuccess (Sucesso - 200) {Date} resultado.versao.data Data da versão.
	 * @apiSuccess (Sucesso - 200) {String} resultado.versao.descricao Descrição do template.
	 * @apiSuccess (Sucesso - 200) {String} resultado.versao.exemplo Exemplo de request para preenchimento do template.
	 * @apiSuccess (Sucesso - 200) {String} resultado.versao.responsavel Identificador do colaborador responsável.
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "corpo": "Prezado {{fulano}}, seu cadastro foi realizado com sucesso.",
	 *       "data":"2017-03-14T16:57:47.405-03:00",
	 *       "descricao": "Template de confirmação de cadastro.",
	 *       "exemplo": "{"fulano":"André Guimarães"}",
	 *       "responsavel": "andre.guimaraes",
	 *     }
	 *     
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
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
	
	
	/**
	 * @api {delete} /templates/excluidos/:template Recuperar template
	 * @apiName undeleteTemplate
	 * @apiGroup Template
	 * @apiVersion 1.0.0
	 * @apiPermission RO_ADMIN_TEMPLATE
	 *
	 * @apiDescription Recupera um template excluído.
	 * 
	 * @apiParam (Path Parameters) {String} template Identificador do template
	 * 	
	 * @apiExample Exemplo de requisição:	
	 *	curl -X DELETE http://<host>/templates-broker/service/templates/excluidos/admp-template-novo-usuario
	 *
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *     
	 * @apiErrorExample {json} Error-Response:
	 * 	HTTP/1.1 500 Internal Server Error
	 * 	{
	 *		"error":"Mensagem de erro."
	 *		"code":"código do erro"
	 *	}
	 */
	@DELETE
	@Path("excluidos/{template}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response undeleteTemplate(@PathParam("template") String nomeTemplate) throws ResourceNotFoundException{
		Template template = getTemplateExcluido(nomeTemplate);
		
		template.setDataExclusao(null);
		
		daoTemplate.merge(template);
		
		return Response.ok().build();
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
