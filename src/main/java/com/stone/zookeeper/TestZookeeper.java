package com.stone.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class TestZookeeper {
	
	private String connectString = "172.30.60.62:2181,172.30.60.63:2181,172.30.60.64:2181";
	private int sessionTimeout = 2000;
	private ZooKeeper zkClient;

	//连接到服务器
	@Before
	public void init() throws IOException {
		zkClient = new ZooKeeper(connectString, sessionTimeout , new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
//				try {
//					List<String> children = zkClient.getChildren("/app1", true);
//					System.out.println("---------------------------");
//					for (String child : children) {
//						System.out.println(child);
//					}
//					System.out.println("---------------------------");
//				} catch (KeeperException e) {
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			}
		});
	}
	
	//创建子节点
	@Test
	public void createNode() throws KeeperException, InterruptedException {
		String path = zkClient.create("/app1", "application1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(path);
	}
	
	//获取子节点并监听节点变化
	@Test
	public void getNode() throws KeeperException, InterruptedException {
		List<String> children = zkClient.getChildren("/app1", true);
		for (String child : children) {
			System.out.println(child);
		}
		//Thread.sleep(Long.MAX_VALUE);
	}
	
	//判断节点是否存在
	@Test
	public void exist() throws KeeperException, InterruptedException {
		Stat stat = zkClient.exists("/app1/app13", false);
		System.out.println(stat==null ? "not exist" : "exist");
	}

}
