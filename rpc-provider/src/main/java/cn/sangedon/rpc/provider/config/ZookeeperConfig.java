package cn.sangedon.rpc.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author dongliangqiong 2021-11-02 12:51
 */
@Data
@Component
@ConfigurationProperties(prefix = "zk")
public class ZookeeperConfig {
    private String host;
}
