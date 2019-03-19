package com.stone.zookeeper;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class DistributeClient {
	
	public static void main(String[] args) throws Exception {
		
		DistributeClient client = new DistributeClient();
		
		// 1 获取ZooKeeper连接
		client.getConnect();
		
		// 2 注册监听
		client.getChildren();
		
		// 3 业务逻辑处理
		client.bussiness();
	}

	private void bussiness() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

	private void getChildren() throws KeeperException, InterruptedException {
		List<String> children = zkClient.getChildren("/app1", true);
		// 存放服务器节点主机名称集合
		ArrayList<Object> hosts = new ArrayList<>();
		for (String child : children) {
			byte[] data = zkClient.getData("/app1/" + child, false, null);
			hosts.add(new String(data));
		}
		// 将所有在线主机名称打印到控制台
		System.out.println(hosts);
	}

	private String connectString = "172.30.60.62:2181,172.30.60.63:2181,172.30.60.64:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	private void getConnect() throws Exception {
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				try {
					getChildren();
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
