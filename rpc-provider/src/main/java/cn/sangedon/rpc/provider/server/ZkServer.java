package cn.sangedon.rpc.provider.server;

import cn.sangedon.rpc.provider.config.ZookeeperConfig;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 维护和zk之间的连接
 *
 * @author dongliangqiong 2021-11-02 13:08
 */
@Component
public class ZkServer {
    public static final String BASE_PATH = "/services/";

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @Bean
    public ZkClient getZkClient() {
        return new ZkClient(zookeeperConfig.getHost());
    }
}
