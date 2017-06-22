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

import br.gov.ans.dao.DAO;
import br.gov.ans.exceptions.ResourceNotFoundException;
import br.gov.ans.templates.modelo.Colaborador;
import br.gov.ans.utils.MessageUtils;

public class ColaboradorDAO {

	@PersistenceContext(unitName = "templates_broker_pu", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@Inject
	private DAO<Colaborador> dao;
	
    @Inject
    private MessageUtils messages;
    
	public Colaborador getColaborador(Long idTemplate, String usuario) throws ResourceNotFoundException{
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("template", idTemplate);
		params.put("usuario", usuario);
		
		List<Colaborador> resultado = dao.executeNamedQuery("Colaborador.recuperarColaborador", params);
		
		if(resultado.size() < 1){
			throw new ResourceNotFoundException(messages.getMessage("erro.colaborador.nao.encontrado", usuario));
		}
		
		return resultado.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> getColaboradores(Long idTemplate, Integer pagina, Integer qtdRegistros){
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("template", idTemplate);
		
		Query query = em.createNamedQuery("Colaborador.recuperarColaboradores");
		
		setParametrosQuery(query, params);
		
		setPaginacaoQuery(query, pagina, qtdRegistros);
		
		return query.getResultList();
	}
	
	public Long countColaboradores(Long idTemplate){
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("template", idTemplate);
		
		Query query = em.createNamedQuery("Colaborador.countColaboradores");
		
		setParametrosQuery(query, params);
		
		return (Long) query.getSingleResult();
	}
	
	public void persist(Colaborador colaborador){
		dao.persist(colaborador);
	}
	
	public void merge(Colaborador colaborador){
		dao.merge(colaborador);
	}
}
