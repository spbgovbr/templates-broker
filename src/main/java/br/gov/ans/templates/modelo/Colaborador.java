package br.gov.ans.templates.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

@XmlRootElement
@Entity
@IdClass(ColaboradorId.class)
@Table(schema = "ANS_GERAL", name = "RL_USUARIO_TEMPLATE_DOCUMENTO")
@NamedQueries({
		@NamedQuery(name = "Colaborador.recuperarColaborador", query = "SELECT c FROM Colaborador c WHERE c.idTemplate = :template AND upper(c.usuario) = upper(:usuario) "
				+ "AND c.dataExclusao is null"),
		@NamedQuery(name = "Colaborador.recuperarColaboradores", query = "SELECT c FROM Colaborador c WHERE c.idTemplate = :template AND c.dataExclusao is null ORDER BY c.usuario ASC"),
		@NamedQuery(name = "Colaborador.countColaboradores", query = "SELECT count(c) FROM Colaborador c WHERE c.idTemplate = :template AND c.dataExclusao is null") })
public class Colaborador implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	@JsonIgnore
	@Id
	@NotNull
	@Column(name = "ID_TEMPLATE_DOCUMENTO")
	private Long idTemplate;

	@Id
	@NotNull
	@Size(max = 30)
	@Column(name = "DE_LOGIN_USUARIO")
	private String usuario;

	@NotNull
	@Column(name = "LG_USUARIO_EDITOR_TEMPLATE")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean editor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_EXCLUSAO_USUARIO_TEMPLATE")
	private Date dataExclusao;

	@XmlTransient
	@JsonIgnore
	public Long getIdTemplate() {
		return idTemplate;
	}
	
	@JsonIgnore
	public void setIdTemplate(Long idTemplate) {
		this.idTemplate = idTemplate;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public boolean getEditor() {
		return editor;
	}

	public void setEditor(boolean editor) {
		this.editor = editor;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date data) {
		this.dataExclusao = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
