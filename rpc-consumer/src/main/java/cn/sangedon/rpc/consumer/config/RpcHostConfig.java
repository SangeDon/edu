package cn.sangedon.rpc.consumer.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Rpc 服务端连接信息配置类
 *
 * @author dongliangqiong 2021-10-25 09:12
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rpc")
public class RpcHostConfig {
    private List<RpcHost> rpcHosts;
}
