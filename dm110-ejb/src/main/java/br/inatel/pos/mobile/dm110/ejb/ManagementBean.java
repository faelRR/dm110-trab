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
			//System.out.println(ip2);
		
			
			List<IPAddressTO> lista = new ArrayList<IPAddressTO>();
			
			for (int i = 0 ; i <= generatedIps.length ; i += 10){
			
				IPAddressTO to = new IPAddressTO();
				
				if (i   <= generatedIps.length){ to.setIp(generatedIps[i  ]); to.setAtivo(false); lista.add(to); }
				if (i+1 <= generatedIps.length){ to.setIp(generatedIps[i+1]); to.setAtivo(false); lista.add(to); }
				if (i+2 <= generatedIps.length){ to.setIp(generatedIps[i+2]); to.setAtivo(false); lista.add(to); }
				if (i+3 <= generatedIps.length){ to.setIp(generatedIps[i+3]); to.setAtivo(false); lista.add(to); }
				if (i+4 <= generatedIps.length){ to.setIp(generatedIps[i+4]); to.setAtivo(false); lista.add(to); }
				if (i+5 <= generatedIps.length){ to.setIp(generatedIps[i+5]); to.setAtivo(false); lista.add(to); }
				if (i+6 <= generatedIps.length){ to.setIp(generatedIps[i+6]); to.setAtivo(false); lista.add(to); }
				if (i+7 <= generatedIps.length){ to.setIp(generatedIps[i+7]); to.setAtivo(false); lista.add(to); }
				if (i+8 <= generatedIps.length){ to.setIp(generatedIps[i+8]); to.setAtivo(false); lista.add(to); }
				if (i+9 <= generatedIps.length){ to.setIp(generatedIps[i+9]); to.setAtivo(false); lista.add(to); }
				
			}
			
			
//			IPAddressTO to = new IPAddressTO();
//			to.setIp(ip2);
//			to.setAtivo(false);
//		
			// jogando para a fila 
		
			ObjectMessage objMessage = session.createObjectMessage();
			//objMessage.setObject(to);
			objMessage.setObject(lista);
			producer.send(objMessage);
			
			
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









