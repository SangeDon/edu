package cn.sangedon.zookeeper.api;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 获取节点数据
 *
 * @author dongliangqiong 2021-10-28 21:58
 */
public class GetNodeData implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("192.168.100.10:2181", 5000, new GetNodeData());
        countDownLatch.await();
    }

    private static void getNodeData() throws Exception {
        byte[] data = zooKeeper.getData("/s1", false, null);
        System.out.println(new String(data));
    }

    private static void getChildrens() throws Exception {
        List<String> children = zooKeeper.getChildren("/s1", true, null);
        System.out.println(children);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == KeeperState.SyncConnected) {
            try {
                getNodeData();
                getChildrens();
                countDownLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
