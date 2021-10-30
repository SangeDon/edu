package cn.sangedon.zookeeper.client;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-30 20:29
 */
public class NodeApi {
    public static void main(String[] args) throws Exception {
        ZkClient zkClient = new ZkClient("47.93.244.54:2181");
        System.out.println("连接创建");

        boolean exists = zkClient.exists("/a3");
        if (!exists) {
            zkClient.createPersistent("/a3", "123");
        }

        Object data = zkClient.readData("/a3");
        System.out.println(data);

        zkClient.subscribeDataChanges("/a3", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("数据变化：" + s + ":::" + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("节点删除：" + s);
            }
        });

        zkClient.writeData("/a3", "456");
        Thread.sleep(1000);

        zkClient.delete("/a3");
        Thread.sleep(1000);
    }
}
