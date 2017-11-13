package com.mzs.mqclient.jms;

import org.apache.activemq.pool.PooledConnection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;


/**
 * 
* @ClassName: JmsSender
* @Description: TODO(消息发送对象)
* @author PYF
* @date 2017年9月8日 上午10:36:32
*
 */
public class JmsSender {
	private String name;
	private Session session;
	private MessageProducer producer;

	public JmsSender(String name, PooledConnection connection,
			Destination type, String subject) throws JMSException {
		this.name = name;
		// 事务性会话，自动确认消息
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 消息的目的地（Queue/Topic）
		if (type.equals(Destination.Topic)) {
			Topic destination = session.createTopic(subject);
			producer = session.createProducer(destination);
		} else {
			Queue destination = session.createQueue(subject);
			producer = session.createProducer(destination);
		}
		// 不持久化消息
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	}

	public String getName() {
		return name;
	}

	public Session getSession() {
		return session;
	}

	public void sendMessage(Message message) throws JMSException {
		producer.send(message);
	}
}
