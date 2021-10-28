package cn.sangedon.rpc.consumer.config;

import lombok.Data;
import lombok.ToString;

/**
 * Rpc 远程连接主机端口信息
 *
 * @author dongliangqiong 2021-10-25 09:21
 */
@Data
@ToString
public class RpcHost {
    private String host;

    private Integer port;
}
