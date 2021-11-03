package cn.sangedon.rpc.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Rpc 服务端启动的地址
 *
 * @author dongliangqiong 2021-11-02 12:55
 */
@Data
@Component
@ConfigurationProperties("rpc.server")
public class RpcServerConfig {
    private String host;

    private Integer port;
}
