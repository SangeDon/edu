package cn.sangedon.rpc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 客户端启动类
 *
 * @author dongliangqiong 2021-10-23 15:04
 */
@SpringBootApplication
public class ClientBootStrap {
    public static void main(String[] args) {
        SpringApplication.run(ClientBootStrap.class, args);
    }
}
