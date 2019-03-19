package com.stone.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class DistributeServer {
	
	public static void main(String[] args) throws Exception {
		
		DistributeServer server = new DistributeServer();
		
		// 1 连接ZooKeeper集群
		server.getConnect();
		
		// 2 注册节点
		server.regist(args[0]);
		
		// 3 业务逻辑处理
		server.bussiness();
		
	}
	
	private void bussiness() throws InterruptedException {
		
		Thread.sleep(Long.MAX_VALUE);
		
	}

	private void regist(String hostname) throws KeeperException, InterruptedException {
		
		String path = zkClient.create("/app1/app", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname + "is online");
		
	}

	private String connectString = "172.30.60.62:2181,172.30.60.63:2181,172.30.60.64:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	private void getConnect() throws Exception {
		
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				
			}
			
		});
	}

}
