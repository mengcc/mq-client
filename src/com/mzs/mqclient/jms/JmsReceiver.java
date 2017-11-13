package com.mzs.mqclient.jms;

import java.util.Enumeration;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.pool.PooledConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
* @ClassName: JmsReceiver
* @Description: TODO(消息接收对象)
* @author PYF
* @date 2017年9月8日 上午10:36:13
*
 */
public class JmsReceiver implements MessageListener {

	private String name;
	private Session session;
	private MessageConsumer consumer;
	private static final Logger logger = LoggerFactory
			.getLogger(JmsReceiver.class);
	public JmsReceiver(String name, PooledConnection connection,
			Destination type, String subject) throws JMSException {
		this.name = name;
		// 事务性会话，自动确认消息
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 消息的目的地（Queue/Topic）
		if (type.equals(Destination.Topic)) {
			Topic destination = session.createTopic(subject);
			consumer = session.createConsumer(destination);
		} else {
			Queue destination = session.createQueue(subject);
			consumer = session.createConsumer(destination);
		}
		consumer.setMessageListener(this);
		
		
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onMessage(Message message) {
		System.out.println("------------------------------------------");
		try {
			if (message instanceof TextMessage) {
				TextMessage msg = (TextMessage) message;
				logger.info("接收消息:"+msg.getText());
			} else if (message instanceof MapMessage) {
				MapMessage msg = (MapMessage) message;
				Enumeration enumer = msg.getMapNames();
				while (enumer.hasMoreElements()) {
					Object obj = enumer.nextElement();
					logger.info("接收消息:"+msg.getObject(obj.toString()));
					System.out.println("接收消息:"+msg.getObject(obj.toString()));
				}
			} else if (message instanceof StreamMessage) {
				StreamMessage msg = (StreamMessage) message;
				logger.info("接收消息:"+msg.readString());
			} else if (message instanceof ObjectMessage) {
				ObjectMessage msg = (ObjectMessage) message;
				logger.info("接收消息:"+msg);
			} else if (message instanceof BytesMessage) {
				BytesMessage msg = (BytesMessage) message;
				byte[] byteContent = new byte[1024];
				int length = -1;
				StringBuffer content = new StringBuffer();
				while ((length = msg.readBytes(byteContent)) != -1) {
					content.append(new String(byteContent, 0, length));
				}
				logger.info("接收消息:"+content.toString());
			} else {
				logger.info("接收消息:"+message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
