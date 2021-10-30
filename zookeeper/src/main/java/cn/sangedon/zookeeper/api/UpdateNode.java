package cn.sangedon.zookeeper.api;

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
public class UpdateNode implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("47.93.244.54:2181", 5000, new UpdateNode());
        countDownLatch.await();
        System.out.println(111);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == KeeperState.SyncConnected) {
            try {
                updateNodeSync();
            } catch (Exception e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
    }

    /**
     * 同步更新数据节点
     */
    private void updateNodeSync() throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData("/b1", false, null);
        System.out.println("修改前的值：" + new String(data));

        Stat stat = zooKeeper.setData("/b1", "修改节点值".getBytes(), -1);
        byte[] data1 = zooKeeper.getData("/b1", false, null);
        System.out.println("修改后的值：" + new String(data1));
    }
}
