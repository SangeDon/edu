package cn.sangedon.zookeeper.client;

import org.I0Itec.zkclient.ZkClient;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-30 20:29
 */
public class CreateSession {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("47.93.244.54:2181");
        System.out.println("连接创建");
    }
}
