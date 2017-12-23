package br.inatel.pos.mobile.dm110.mdb;

import java.awt.List;
import java.util.ArrayList;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import br.inatel.pos.mobile.dm110.dao.AddressDAO;
import br.inatel.pos.mobile.dm110.entities.IPAddress;
import br.inatel.pos.mobile.dm110.to.IPAddressList;
import br.inatel.pos.mobile.dm110.to.IPAddressTO;
import br.inatel.pos.mobile.dm110.util.TestRuntime;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType",
								  propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination",
								  propertyValue = "java:/jms/queue/managementqueue"),
		@ActivationConfigProperty(propertyName = "maxSession",
								  propertyValue = "3")
})
public class ManagementMDB implements MessageListener {

	@EJB
	private AddressDAO dao;

	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof ObjectMessage) {
				ObjectMessage objMessage = (ObjectMessage) message;
				Object obj = objMessage.getObject();
							
				System.out.println("## recebi mensagem da fila");
				
				if (obj instanceof IPAddressList) {				
					// pegar o vetor de no maximo 10 posições
					
					String[] listaIP = ((IPAddressList) obj).getLista();
					
					for (String ip2 : listaIP) {
						
						// confere se o que esta na fila esta correto
						if (ip2 != null && ip2.length() > 0){						
							IPAddress ip = new IPAddress();
							ip.setIp(ip2);
							// fazer o ping para poder salvar
							ip.setAtivo(TestRuntime.execPing(ip2));					
							dao.insert(ip);
						}
					}
				}
			}
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}





