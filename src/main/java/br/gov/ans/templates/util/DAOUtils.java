package br.gov.ans.templates.util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Query;

public class DAOUtils {
	@SuppressWarnings("rawtypes")
	public static void setParametrosQuery(Query query, HashMap<String, Object> parametros){
		if ((parametros != null) && (!parametros.isEmpty())) {
			for (Map.Entry entry : parametros.entrySet()) {
				query.setParameter((String) entry.getKey(),entry.getValue());
			}
		}
	}
	
	public static void setPaginacaoQuery(Query query, Integer pagina, Integer qtdRegistros){
		if(qtdRegistros == null){
			qtdRegistros = Constantes.TAMANHO_PAGINA_PADRAO;
		}
		
		if(pagina == null){
			pagina = 1;
		}
		
		int firstResult = ((pagina - 1)* qtdRegistros);		
		query.setFirstResult(firstResult);
		query.setMaxResults(qtdRegistros);		
	}	
}
