package cn.sangedon.dubbo.consumer;

import cn.sangedon.dubbo.service.api.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author dongliangqiong 2021-11-04 14:22
 */
@Component("helloConsumer")
public class HelloConsumer {
    @Reference
    private HelloService helloService;

    public String sayHello(String hello) {
        return helloService.sayHello(hello);
    }
}
