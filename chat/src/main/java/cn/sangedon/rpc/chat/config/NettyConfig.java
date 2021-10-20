package cn.sangedon.rpc.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dongliangqiong 2021-10-20 20:40
 * @Description TODO
 */
@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyConfig {
    /**
     * netty服务的端口号
     */
    private Integer port;

    /**
     * netty服务路径
     */
    private String path;
}
