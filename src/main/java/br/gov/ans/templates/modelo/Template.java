package br.gov.ans.templates.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Type;

@XmlRootElement
@Entity
@Table(schema="ANS_GERAL",name="TB_TEMPLATE_DOCUMENTO")
@NamedQueries({@NamedQuery(name = "Template.recuperarPeloNome", query = "SELECT t FROM Template t WHERE upper(t.nome) = upper(:nome) AND t.dataExclusao is null")})
public class Template implements Serializable{

	private static final long serialVersionUID = 1L;

	@XmlTransient
	@JsonIgnore
	@Id
	@Column(name="ID_TEMPLATE_DOCUMENTO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max=60)
	@Column(name="NM_TEMPLATE_DOCUMENTO", unique=true)
	private String nome;
	
	@NotNull
	@Size(max=200)
	@Column(name="DE_TEMPLATE_DOCUMENTO")
	private String descricao;
	
	@NotNull	
	@Column(name="LG_TEMPLATE_DOCUMENTO_RESTRITO")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean restrito;
	
	@NotNull
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(name="TX_EXEMPLO_TEMPLATE_DOCUMENTO")
	private String exemplo;
	
	@XmlTransient
	@NotNull
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name="OB_CORPO_TEMPLATE_DOCUMENTO")
	private byte[] corpo;

	@JsonProperty("corpo")
	@XmlElement(name = "corpo")
	public String getCorpoString() {		
		return new String(corpo);
	}
	
	@NotNull
	@Size(max=30)
	@Column(name="DE_LOGIN_USUARIO_RPSV_TMPL")	
	private String responsavel;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_CADASTRO_TEMPLATE")
	private Date dataCadastro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_EXCLUSAO_TEMPLATE")
	private Date dataExclusao;
	
	
	public Template(){
	}
	
	public Template(br.gov.ans.templates.to.Template template){
		setNome(template.getNome());
		setDescricao(template.getDescricao());
		setResponsavel(template.getResponsavel());
		setRestrito(template.getRestrito());
		setCorpo(template.getCorpo() == null? null : template.getCorpo().getBytes());
		setExemplo(template.getExemplo());
		
		setDataCadastro(new Date());
	}

	@XmlTransient
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Boolean getRestrito() {
		return restrito;
	}
	public void setRestrito(Boolean restrito) {
		this.restrito = restrito;
	}

	public String getExemplo() {
		return exemplo;
	}

	public void setExemplo(String exemplo) {
		this.exemplo = exemplo;
	}

	@XmlTransient
	public byte[] getCorpo() {
		return corpo;
	}
	
	public void setCorpo(byte[] corpo) {		
		this.corpo = corpo;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date data) {
		this.dataCadastro = data;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}	
}
