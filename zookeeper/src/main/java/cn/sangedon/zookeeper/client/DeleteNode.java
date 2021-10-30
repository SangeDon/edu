package cn.sangedon.zookeeper.client;

import org.I0Itec.zkclient.ZkClient;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-30 20:29
 */
public class DeleteNode {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("47.93.244.54:2181");
        System.out.println("连接创建");

        String path = "/a1/b1";
        zkClient.createPersistent(path + "/c1");
        boolean deleteRecursive = zkClient.deleteRecursive(path);
        System.out.println("递归删除：" + deleteRecursive);
    }
}
