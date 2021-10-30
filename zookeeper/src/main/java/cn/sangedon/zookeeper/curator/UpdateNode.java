package cn.sangedon.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-30 21:33
 */
public class UpdateNode {
    public static void main(String[] args) throws Exception {
        RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                                                         .connectString("47.93.244.54:2181")
                                                         .sessionTimeoutMs(50000)
                                                         .connectionTimeoutMs(30000)
                                                         .retryPolicy(retry)
                                                         .namespace("base")
                                                         .build();
        client.start();
        System.out.println("会话建立了");

        String path = "/c1/a1";
        byte[] bytes = client.getData().forPath(path);
        System.out.println("123:" + new String(bytes));

        Stat stat = new Stat();
        System.out.println(stat);
        client.getData().storingStatIn(stat).forPath(path);
        System.out.println(stat);

        System.out.println("更新1");
        client.setData().withVersion(stat.getVersion()).forPath(path, "修改内容".getBytes());
        System.out.println(stat);

//        System.out.println("更新2");
//        client.setData().withVersion(stat.getVersion()).forPath(path, "修改内容".getBytes());
//        System.out.println(stat);
//        client.getData().storingStatIn(stat).forPath(path);
//        System.out.println(stat);
    }
}
