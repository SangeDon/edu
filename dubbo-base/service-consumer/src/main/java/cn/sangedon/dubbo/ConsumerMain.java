package cn.sangedon.dubbo;

import cn.sangedon.dubbo.consumer.HelloConsumer;
import java.io.IOException;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * TODO
 *
 * @author dongliangqiong 2021-11-04 14:27
 */
public class ConsumerMain {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerMain.class);
        context.start();

        HelloConsumer helloConsumer = context.getBean(HelloConsumer.class);
        while (true) {
            System.in.read();
            String hello = helloConsumer.sayHello("你好");
            System.out.println(hello);
        }
    }

    @Configuration
    @PropertySource("classpath://dubbo-consumer.properties")
    @ComponentScan(basePackages = "cn.sangedon.dubbo.consumer")
    @EnableDubbo
    static class ConsumerConfiguration {

    }
}
