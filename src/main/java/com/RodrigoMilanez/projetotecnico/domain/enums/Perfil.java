package com.RodrigoMilanez.projetotecnico.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADM"),
	ATENDENTE(2, "ROLE_ATENDENTE"),
	TECNICO(3, "ROLE_TECNICO");
	
	private int cod;
	private String descricao;
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for(Perfil x : Perfil.values()) {
			if (cod.equals(x.getCod())){
				return x;
			}
		}
		throw new IllegalArgumentException("código inválido"+ cod);
	}
	
}