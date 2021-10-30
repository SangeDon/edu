package cn.sangedon.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-30 21:33
 */
public class CreateSession {
    public static void main(String[] args) throws Exception {
        RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("47.93.244.54:2181", retry);
        curatorFramework.start();
        System.out.println("会话1建立了");

        CuratorFramework client = CuratorFrameworkFactory.builder()
                                                         .connectString("47.93.244.54:2181")
                                                         .sessionTimeoutMs(50000)
                                                         .connectionTimeoutMs(30000)
                                                         .retryPolicy(retry)
                                                         .namespace("base")
                                                         .build();
        client.start();
        System.out.println("会话2建立了");

        String path = "/c1/a1";
        String s = client.create().creatingParentsIfNeeded()
                         .withMode(CreateMode.PERSISTENT).forPath(path, "init".getBytes());
        System.out.println("123:" + s);
    }
}
