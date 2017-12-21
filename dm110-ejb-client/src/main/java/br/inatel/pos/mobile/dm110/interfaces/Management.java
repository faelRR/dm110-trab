package br.inatel.pos.mobile.dm110.interfaces;

import br.inatel.pos.mobile.dm110.to.IPAddressTO;


public interface Management {

	void addNewIP(String ip , String mask);

	boolean listIP(String ip);

}
