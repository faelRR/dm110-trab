package br.inatel.pos.mobile.dm110.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.inatel.pos.mobile.dm110.entities.IPAddress;
import br.inatel.pos.mobile.dm110.to.IPAddressTO;

@Stateless
public class AddressDAO {

	@PersistenceContext(unitName = "poller")
	private EntityManager em;

	public void insert(IPAddress ipAddress) {	
		em.merge(ipAddress);
	}

	public String find(String ip) {
	    
		String retorno;
		// tenta pegar o primeiro retorno da consulta
		if ((em.createQuery("from IPAddress a where a.ip=:ip", IPAddress.class).setParameter("ip", ip).getResultList()).size() == 0){
			retorno = "IP não encontrado";
		}else{
			if (em.createQuery("from IPAddress a where a.ip=:ip", IPAddress.class).setParameter("ip", ip).getResultList().get(0).isAtivo()){
				retorno = "IP Ativo"; 						
			}else{
				retorno = "IP Inativo";
			}
				
		}
		
		return retorno;
	}

}
