package br.gov.ans.templates.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
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
@IdClass(VersaoId.class)
@Table(schema="ANS_GERAL",name="TB_VERSAO_TEMPLATE_DOCUMENTO")
@NamedQueries({
	@NamedQuery(name = "Versao.recuperarVersoes", query = "SELECT v FROM Versao v WHERE v.idTemplate = :template ORDER BY v.data DESC")})
public class Versao implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@XmlTransient
	@Id
	@NotNull
	@Column(name="ID_TEMPLATE_DOCUMENTO")
	private Long idTemplate;
	
	@Id
	@NotNull
	@Size(max=30)
	@Column(name="DE_LOGIN_USUARIO")	
	private String responsavel;
	
	@Id
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_VERSAO_TEMPLATE")
	private Date data;
	
	@NotNull
	@Size(max=200)
	@Column(name="DE_TEMPLATE_DOCUMENTO")
	private String descricao;
	
	@NotNull
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(name="TX_EXEMPLO_TEMPLATE_DOCUMENTO")
	private String exemplo;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name="OB_CORPO_TEMPLATE_DOCUMENTO")
	private byte[] corpo;

	public Versao(){		
	}
	
	public Versao(Template template){
		this.idTemplate = template.getId();
		this.responsavel = template.getResponsavel();
		this.data = template.getDataCadastro();
		this.descricao = template.getDescricao();
		this.corpo = template.getCorpo();
		this.exemplo = template.getExemplo();
	}
	
	@JsonIgnore
	@XmlTransient
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getExemplo() {
		return exemplo;
	}

	public void setExemplo(String exemplo) {
		this.exemplo = exemplo;
	}

	public byte[] getCorpo() {
		return corpo;
	}

	public void setCorpo(byte[] corpo) {
		this.corpo = corpo;
	}
}
