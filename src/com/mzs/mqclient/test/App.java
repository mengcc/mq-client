/**
 * All rights Reserved, Designed By tydic.com
 * @Author mengzs
 * @Date 2017年11月13日 下午4:01:10
 */
package com.mzs.mqclient.test;

import java.util.UUID;

import javax.jms.MapMessage;
import javax.jms.Session;

import com.mzs.mqclient.jms.ActiveMQPlugin;
import com.mzs.mqclient.jms.ActiveMQPoolManager;
import com.mzs.mqclient.jms.Destination;
import com.mzs.mqclient.jms.JmsSender;

/**
 * @Author mengzs
 * @Date 2017年11月13日
 */
public class App {

	public static void main(String[] args) {

		
		ActiveMQPlugin activeMQPlugin = new ActiveMQPlugin("failover:tcp://127.0.0.1:61616","demo");
		activeMQPlugin.start();
		try {
			JmsSender jmsSender = ActiveMQPoolManager.getSender("demo");
			if (jmsSender == null) {
				jmsSender = new JmsSender("demo", ActiveMQPoolManager.getConnection("demo"), Destination.Queue,"demo");
				ActiveMQPoolManager.addSender(jmsSender);
			}
			Session session = jmsSender.getSession();
			MapMessage message = session.createMapMessage();
			message.setString("startCity", "kmg");
			message.setString("flightNo", "KY8213");
			String key = UUID.randomUUID().toString();
			message.setString("key", key);
			jmsSender.sendMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
