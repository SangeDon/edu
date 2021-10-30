package cn.sangedon.zookeeper.client;

import org.I0Itec.zkclient.ZkClient;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-30 20:29
 */
public class CreateNode {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("47.93.244.54:2181");
        System.out.println("连接创建");

        zkClient.createPersistent("/a1/b1", true);
        zkClient.createPersistent("/a1/b2", true);
        System.out.println("递归节点创建");
    }
}
