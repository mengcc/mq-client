package com.mzs.mqclient.jms;


import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: ActiveMQPlugin
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author PYF
 * @date 2017年9月8日 上午10:35:54
 * 
 */

public class ActiveMQPlugin {

	private static final Logger logger = LoggerFactory.getLogger(ActiveMQPlugin.class);

	private String url;

	private String name;

	public ActiveMQPlugin(String url, String name) {
		this.url = url;
		this.name = name;
	}

	private ActiveMQConnectionFactory activeMQConnectionFactory = null;

	public boolean start() {
		logger.info("初始化activeMQ配置");
		try {
			activeMQConnectionFactory = new ActiveMQConnectionFactory();
			activeMQConnectionFactory.setUserName(ActiveMQConnection.DEFAULT_USER);
			activeMQConnectionFactory.setPassword(ActiveMQConnection.DEFAULT_PASSWORD);
			activeMQConnectionFactory.setBrokerURL(url);
			activeMQConnectionFactory.setDispatchAsync(true);// 异步发送消息
			PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
			pooledConnectionFactory.setConnectionFactory(activeMQConnectionFactory);
			pooledConnectionFactory.setMaximumActiveSessionPerConnection(200);
			pooledConnectionFactory.setIdleTimeout(120);
			pooledConnectionFactory.setMaxConnections(5);
			pooledConnectionFactory.setBlockIfSessionPoolIsFull(true);

			PooledConnection connection = (PooledConnection) pooledConnectionFactory.createConnection();
			connection.start();
			ActiveMQPoolManager.pooledConnectionMap.put(name, connection);
			logger.info("初始化activeMQ配置完成..........");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
