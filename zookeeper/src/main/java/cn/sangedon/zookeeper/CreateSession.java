package cn.sangedon.zookeeper;

import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 和zookeeer建立会话
 *
 * @author dongliangqiong 2021-10-28 21:43
 */
public class CreateSession implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
//        ZooKeeper zooKeeper = new ZooKeeper("47.93.244.54:2181", 5000, new CreateSession());
        ZooKeeper zooKeeper = new ZooKeeper("192.168.100.10:2181", 5000, new CreateSession());
        System.out.println(zooKeeper.getState());

        countDownLatch.await();
        System.out.println("connected");
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
    }
}
