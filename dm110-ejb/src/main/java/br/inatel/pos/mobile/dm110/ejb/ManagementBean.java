package br.inatel.pos.mobile.dm110.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import br.inatel.pos.mobile.dm110.dao.AddressDAO;
import br.inatel.pos.mobile.dm110.interfaces.ManagementLocal;
import br.inatel.pos.mobile.dm110.interfaces.ManagementRemote;
import br.inatel.pos.mobile.dm110.to.IPAddressList;
import br.inatel.pos.mobile.dm110.to.IPAddressTO;
import br.inatel.pos.mobile.dm110.util.NetworkIpGen;

@Stateless
@Local(ManagementLocal.class)
@Remote(ManagementRemote.class)
public class ManagementBean implements ManagementLocal, ManagementRemote {

	@EJB
	private AddressDAO dao;

	@Resource(lookup = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(lookup = "java:/jms/queue/managementqueue")
	private Queue queue;

	public String[] findIP(String ip , String mask){
		
		// chavama a funcao de descobrir ip
		
		// funcao para envio de 10 em 10
		
		// remover endereços de broadcast 
		
		return NetworkIpGen.generateIps(ip, Integer.parseInt(mask));
	}
	
	@Override
	public void addNewIP(String ip , String mask) {
		
		String[] generatedIps = findIP(ip , mask);
		
		try (
				Connection connection = connectionFactory.createConnection();
				Session session = connection.createSession();
				MessageProducer producer = session.createProducer(queue);
		) {
		
			// generatedIps = lista de todos os ips encontrados
			//for (String ip2 : generatedIps) {
				
			// mostrnado ips
			
			System.out.println("## preenchendo lista");
					
			for (int i = 0 ; i <= generatedIps.length - 1 ; i += 10){
			
				String[] lista = new String[10];
							
				if (i   < generatedIps.length-1){ lista[0] = (generatedIps[i]  ) ; }
				if (i+1 < generatedIps.length-1){ lista[1] = (generatedIps[i+1]) ; }
				if (i+2 < generatedIps.length-1){ lista[2] = (generatedIps[i+2]) ; }
				if (i+3 < generatedIps.length-1){ lista[3] = (generatedIps[i+3]) ; }
				if (i+4 < generatedIps.length-1){ lista[4] = (generatedIps[i+4]) ; }
				if (i+5 < generatedIps.length-1){ lista[5] = (generatedIps[i+5]) ; }
				if (i+6 < generatedIps.length-1){ lista[6] = (generatedIps[i+6]) ; }
				if (i+7 < generatedIps.length-1){ lista[7] = (generatedIps[i+7]) ; }
				if (i+8 < generatedIps.length-1){ lista[8] = (generatedIps[i+8]) ; }
				if (i+9 < generatedIps.length-1){ lista[9] = (generatedIps[i+9]) ; }	
			
				IPAddressList listaIP = new IPAddressList();
				listaIP.setLista(lista);			
			
				// jogando a lista para a fila 		
				ObjectMessage objMessage = session.createObjectMessage();
				objMessage.setObject(listaIP);
				producer.send(objMessage);
				
			}
			
			System.out.println("## terminou a fila");
			
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
		
		System.out.println("## terminou a funcao");
		
		
	}


	@Override
	public String listIP(String ip){
		return dao.find(ip);
//				.stream()
//				.map(a -> {
//					IPAddressTO to = new IPAddressTO();
//					to.setIp(a.getIP());
//					to.setAtivo(a.getAtivo());
//					return to;
//				}).collect(Collectors.toList())
//				.toArray(new ProductTO[0]);
	}

}









