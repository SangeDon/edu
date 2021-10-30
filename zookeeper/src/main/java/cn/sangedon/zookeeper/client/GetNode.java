package cn.sangedon.zookeeper.client;

import java.util.List;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-30 20:29
 */
public class GetNode {
    public static void main(String[] args) throws Exception {
        ZkClient zkClient = new ZkClient("47.93.244.54:2181");
        System.out.println("连接创建");

        List<String> children = zkClient.getChildren("/a1");
        System.out.println(children);
        zkClient.deleteRecursive("/a2");
        zkClient.subscribeChildChanges("/a2", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> list) throws Exception {
                System.out.println("监听" + parentPath + ":::" + list);
            }
        });

        zkClient.createPersistent("/a2");
        System.out.println("/a2 创建");
        Thread.sleep(5000);
        zkClient.createPersistent("/a2/b1");
        Thread.sleep(5000);
    }
}
