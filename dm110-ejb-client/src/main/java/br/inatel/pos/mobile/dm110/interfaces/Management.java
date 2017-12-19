package br.inatel.pos.mobile.dm110.interfaces;

import br.inatel.pos.mobile.dm110.to.IPAddressTO;


public interface Management {

	void addNewIP(IPAddressTO adressIP);

	boolean listIP(String ip);

}
