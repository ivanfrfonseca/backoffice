package br.com.cpqd.orbillbackoffice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Table(name = "script")
@Getter
@Setter
public class ScriptModel  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titulo;
	private String categoria;
	private String caminho;
	private int fkIdEmpresa;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getTitulo() {
		return titulo;
	}
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
	public int getFkIdEmpresa() {
		return fkIdEmpresa;
	}
	public void setFkIdEmpresa(int fkIdEmpresa) {
		this.fkIdEmpresa = fkIdEmpresa;
	}
		
}
