package br.gov.ans.templates.modelo;

import java.io.Serializable;

public class ColaboradorId implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idTemplate;
	private String usuario;

	public Long getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(Long idTemplate) {
		this.idTemplate = idTemplate;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTemplate == null) ? 0 : idTemplate.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		ColaboradorId other = (ColaboradorId) obj;
		if (idTemplate == null) {
			if (other.idTemplate != null)
				return false;
		} else if (!idTemplate.equals(other.idTemplate))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
}
