package com.mzs.mqclient.jms;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.activemq.pool.PooledConnection;

/**
 * 
* @ClassName: ActiveMQPoolManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author PYF
* @date 2017年9月8日 上午10:34:07
*
 */
public class ActiveMQPoolManager {

	public static final ConcurrentHashMap<String, PooledConnection> pooledConnectionMap = new ConcurrentHashMap<String, PooledConnection>();
	public static final ConcurrentHashMap<String, JmsSender> senderMap = new ConcurrentHashMap<String, JmsSender>();
	public static final ConcurrentHashMap<String, JmsReceiver> receiverMap = new ConcurrentHashMap<String, JmsReceiver>();
	public static final String defaultName = "main";


	public static void addSender(JmsSender sender) {
		senderMap.put(sender.getName(), sender);
	}

	
	/**
	 * 
	* @Title: getSender 
	* @Description: TODO(获取发送对象) 
	* @param @param name
	* @param @return    设定文件 
	* @return JmsSender    返回类型 
	* @throws
	 */
	public static JmsSender getSender(String name) {
		return senderMap.get(name);
	}

	public static void addReceiver(JmsReceiver receiver) {
		receiverMap.put(receiver.getName(), receiver);
	}

	/**
	 * 
	* @Title: getReceiver 
	* @Description: TODO(获取接收对象) 
	* @param @param name
	* @param @return    设定文件 
	* @return JmsReceiver    返回类型 
	* @throws
	 */
	public static JmsReceiver getReceiver(String name) {
		return receiverMap.get(name);
	}

	/**
	 * 
	* @Title: addConnection 
	* @Description: TODO(添加连接) 
	* @param @param connectionName
	* @param @param connection    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void addConnection(String connectionName,
			PooledConnection connection) {
		pooledConnectionMap.put(connectionName, connection);
	}

	/**
	 * 
	* @Title: getConnection 
	* @Description: TODO(获取默认连接) 
	* @param @return    设定文件 
	* @return PooledConnection    返回类型 
	* @throws
	 */
	public static PooledConnection getConnection() {
		return pooledConnectionMap.get(defaultName);
	}

	/**
	 * 
	* @Title: getConnection 
	* @Description: TODO(根据指定名字获取连接) 
	* @param @param connectionName
	* @param @return    设定文件 
	* @return PooledConnection    返回类型 
	* @throws
	 */
	public static PooledConnection getConnection(String connectionName) {
		return pooledConnectionMap.get(connectionName);
	}
}
