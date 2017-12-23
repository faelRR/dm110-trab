package br.inatel.pos.mobile.dm110.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import br.inatel.pos.mobile.dm110.dao.AddressDAO;
import br.inatel.pos.mobile.dm110.entities.IPAddress;
import br.inatel.pos.mobile.dm110.to.IPAddressTO;
import br.inatel.pos.mobile.dm110.util.TestRuntime;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType",
								  propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination",
								  propertyValue = "java:/jms/queue/managementqueue"),
		@ActivationConfigProperty(propertyName = "maxSession",
								  propertyValue = "10")
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
							
				//System.out.println("## recebi mensagem da fila");
				
				if (obj instanceof IPAddressTO) {				
					// pegar o vetor de no maximo 10 posições
					
					pegas as 10 posição
					
					IPAddressTO to = (IPAddressTO) obj;					
					IPAddress ip = new IPAddress();
					ip.setIp(to.getIp());
					// fazer o ping para poder salvar
					ip.setAtivo(TestRuntime.execPing(to.getIp()));					
					dao.insert(ip);
				}
			}
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}





