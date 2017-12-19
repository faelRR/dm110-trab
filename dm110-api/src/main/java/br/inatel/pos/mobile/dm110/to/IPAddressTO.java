package br.inatel.pos.mobile.dm110.to;

import java.io.Serializable;

public class IPAddressTO implements Serializable {

	private static final long serialVersionUID = -5540622100281312954L;

	private String ip;
	private boolean ativo;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	
	
	
}
