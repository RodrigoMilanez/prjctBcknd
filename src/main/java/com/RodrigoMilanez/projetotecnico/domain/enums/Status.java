package com.RodrigoMilanez.projetotecnico.domain.enums;

public enum Status {

	DIAGNOSTICO (1, "DIAGNOSTICO"), 
	AGUARDANDO_CLIENTE (2, "AGUARDANDO"),
	RECUSADO (3, "RECUSADO"),	
	REPARO (4, "REPARO"),
	CONCLUIDO (5, "CONCLUIDO"),
	CANCELADO(6, "CANCELADO");
	
	private int cod;
	private String descricao;
	private Status(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static Status toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for(Status x : Status.values()) {
			if (cod.equals(x.getCod())){
				return x;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + cod);
	}
}
