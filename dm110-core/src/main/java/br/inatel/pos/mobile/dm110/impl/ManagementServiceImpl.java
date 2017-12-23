package br.inatel.pos.mobile.dm110.impl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import br.inatel.pos.mobile.dm110.api.ManagementService;
import br.inatel.pos.mobile.dm110.interfaces.ManagementRemote;
import br.inatel.pos.mobile.dm110.to.IPAddressTO;

@RequestScoped
public class ManagementServiceImpl implements ManagementService {

	@EJB(lookup = "java:app/dm110-ejb-1.0.0-SNAPSHOT/ManagementBean!br.inatel.pos.mobile.dm110.interfaces.ManagementRemote")
	private ManagementRemote managementBean;

	@Override
	public void addNewIP(String ip , String mask) {		
		managementBean.addNewIP( ip , mask );
	}

	@Override
	public String listIP(String ip) {
		return managementBean.listIP(ip);
	}

}
