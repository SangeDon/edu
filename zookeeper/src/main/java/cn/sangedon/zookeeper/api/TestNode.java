package cn.sangedon.zookeeper.api;

import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 创建节点
 *
 * @author dongliangqiong 2021-10-28 21:58
 */
public class CreateNode implements Watcher {
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper("47.93.244.54:2181", 5000, new CreateNode());
//        System.out.println(zooKeeper.getState());
        countDownLatch.await();
        createNodeSync();
        System.out.println(111);
//        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void createNodeSync() throws Exception {
        String node1 = zooKeeper.create("/b1", "e-sangedon1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        String node2 = zooKeeper.create("/b2", "e-sangedon2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        String node3 = zooKeeper.create("/b3", "e-sangedon3".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        String node4 = zooKeeper.create("/b4", "e-sangedon4".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("持久" + node1);
        System.out.println("临时" + node2);
        System.out.println("持久-顺序" + node3);
        System.out.println("临时-顺序" + node4);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == KeeperState.SyncConnected) {
            countDownLatch.countDown();
            /*try {
                createNodeSync();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }
}
