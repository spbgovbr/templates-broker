package br.gov.ans.templates.to;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Template {
	private String nome;
	private String descricao;
	private Boolean restrito;
	private String exemplo;
	private String corpo;
	private String responsavel;
	private Date data;

	public Template(){		
	}
	
	public Template(br.gov.ans.templates.modelo.Template template){
		setNome(template.getNome());
		setDescricao(template.getDescricao());
		setRestrito(template.getRestrito());
		setExemplo(template.getExemplo());
		setCorpo(new String(template.getCorpo()));
		setResponsavel(template.getResponsavel());
		setData(template.getDataCadastro());
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
		if(restrito == null){
			return false;
		}
		
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

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
