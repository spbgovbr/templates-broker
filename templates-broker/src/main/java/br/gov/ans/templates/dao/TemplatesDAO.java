package br.gov.ans.templates.dao;

import static br.gov.ans.templates.util.DAOUtils.setPaginacaoQuery;
import static br.gov.ans.templates.util.DAOUtils.setParametrosQuery;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import br.gov.ans.dao.DAO;
import br.gov.ans.templates.modelo.Template;;

public class TemplatesDAO {
	
	@PersistenceContext(unitName = "templates_broker_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@Inject
	private DAO<Template> dao;
	
	@SuppressWarnings("unchecked")
	public List<Template> getTemplates(String filtro, Integer pagina, Integer qtdRegistros){
		HashMap<String, Object> parametros =  new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("SELECT t FROM Template t ");
		
		if(StringUtils.isNotBlank(filtro)){
			sql.append("WHERE upper(t.nome) LIKE upper(:filtro) ");
			parametros.put("filtro", "%"+filtro+"%");
		}
		
		sql.append(AndOrWhere(sql) + " t.dataExclusao is null ");
		
		sql.append("order by nome asc ");
		
		Query query = em.createQuery(sql.toString());
		
		setParametrosQuery(query, parametros);
		
		setPaginacaoQuery(query, pagina, qtdRegistros);
		
		return query.getResultList();
	}

	public Long countTemplates(String filtro){
		HashMap<String, Object> parametros =  new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder("SELECT count(t) FROM Template t ");
		
		if(StringUtils.isNotBlank(filtro)){
			sql.append("WHERE upper(t.nome) LIKE upper(:filtro) ");
			parametros.put("filtro", "%"+filtro+"%");
		}
		
		sql.append(AndOrWhere(sql) + " t.dataExclusao is null ");
		
		Query query = em.createQuery(sql.toString());
		
		setParametrosQuery(query, parametros);
		
		return (Long) query.getSingleResult();
	}
	
	public Template getTemplate(String nome){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("nome", nome);
		
		List<Template> resultado = dao.executeNamedQuery("Template.recuperarPeloNome", params);
		
		if(resultado.size() < 1){
			return null;			
		}
		
		return resultado.get(0);	
	}
	
	public String AndOrWhere(StringBuilder sql){
		if(sql.toString().contains("WHERE")){
			return "AND";
		}
		
		return "WHERE";
	}
	
	public void persist(Template template){
		dao.persist(template);
	}

	public void merge(Template template) {
		dao.merge(template);		
	}
}
