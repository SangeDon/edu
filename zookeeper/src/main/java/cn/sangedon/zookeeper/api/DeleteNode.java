package cn.sangedon.zookeeper.api;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 创建节点
 *
 * @author dongliangqiong 2021-10-28 21:58
 */
public class DeleteNode implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("47.93.244.54:2181", 5000, new DeleteNode());
        countDownLatch.await();
        System.out.println(111);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == KeeperState.SyncConnected) {
            try {
                deleteNodeSync();
            } catch (Exception e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
    }

    /**
     * 同步更新数据节点
     */
    private void deleteNodeSync() throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists("/b1", false);
        System.out.println(exists == null ? "节点不存在" : "节点存在");
        Optional.ofNullable(exists).ifPresent(item -> {
            try {
                zooKeeper.delete("/b1", -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Stat s1 = zooKeeper.exists("/b1", false);
        if (s1 == null) {
            System.out.println(s1 == null ? "节点不存在" : "节点存在");
        } else {
            byte[] data1 = zooKeeper.getData("/b1", false, null);
            System.out.println("删除后的值：" + new String(data1));
        }
    }
}
