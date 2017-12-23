package br.inatel.pos.mobile.dm110.to;

import java.io.Serializable;

public class IPAddressList implements Serializable {

	private static final long serialVersionUID = 4825240682392122259L;
	
	
	private String[] lista = new String[10];


	public String[] getLista() {
		return lista;
	}


	public void setLista(String[] lista) {
		this.lista = lista;
	}


}
