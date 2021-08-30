package com.RodrigoMilanez.projetotecnico.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Cliente implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message="Este campo deve ser preenchido")
	@Length(min=5, max=80, message="O tamanho deve ter de 5 a 80 caracteres")
	private String nome;
	@NotEmpty(message="Este campo deve ser preenchido")
	@Email
	private String email;
	@NotEmpty(message="Este campo deve ser preenchido")
	private String telefone;
	@NotEmpty(message="Este campo deve ser preenchido")
	private String endereco;
	@NotEmpty(message="Este campo deve ser preenchido")
	private String cpf;
	
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.PERSIST, mappedBy = "cliente")
	private List<OrdemDeServico> ordens = new ArrayList<>();
	
	public Cliente () {
		
	}

	public Cliente(Integer id, String nome, String email, String telefone, String endereco, String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
		this.cpf= cpf;
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<OrdemDeServico> getOrdens() {
		return ordens;
	}

	public void setOrdens(List<OrdemDeServico> ordens) {
		this.ordens = ordens;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
