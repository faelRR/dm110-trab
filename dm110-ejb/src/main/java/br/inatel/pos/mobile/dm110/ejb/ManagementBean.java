package br.inatel.pos.mobile.dm110.ejb;

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

	@Override
	public void addNewIP(IPAddressTO to) {
		try (
				Connection connection = connectionFactory.createConnection();
				Session session = connection.createSession();
				MessageProducer producer = session.createProducer(queue);
		) {
			ObjectMessage objMessage = session.createObjectMessage();
			objMessage.setObject(to);
			producer.send(objMessage);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public boolean listIP(String ip){
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









