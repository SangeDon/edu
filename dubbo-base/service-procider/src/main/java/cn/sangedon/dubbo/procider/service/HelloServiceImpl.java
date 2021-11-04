package cn.sangedon.dubbo.procider.service;

import cn.sangedon.dubbo.service.api.HelloService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 服务接口实现
 *
 * @author dongliangqiong 2021-11-04 14:09
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String hello) {
        return "hello dubbo service!" + hello;
    }
}
