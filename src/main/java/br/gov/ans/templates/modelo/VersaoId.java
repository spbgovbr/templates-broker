package br.gov.ans.templates.modelo;

import java.io.Serializable;
import java.util.Date;

public class VersaoId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idTemplate;
	private String responsavel;
	private Date data;

	public Long getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(Long idTemplate) {
		this.idTemplate = idTemplate;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String usuario) {
		this.responsavel = usuario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((idTemplate == null) ? 0 : idTemplate.hashCode());
		result = prime * result + ((responsavel == null) ? 0 : responsavel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VersaoId other = (VersaoId) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (idTemplate == null) {
			if (other.idTemplate != null)
				return false;
		} else if (!idTemplate.equals(other.idTemplate))
			return false;
		if (responsavel == null) {
			if (other.responsavel != null)
				return false;
		} else if (!responsavel.equals(other.responsavel))
			return false;
		return true;
	}
}
