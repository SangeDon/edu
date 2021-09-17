package cn.sangedon.config;

import cn.sangedon.pojo.SimpleBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongliangqiong 2021-09-11 15:36
 */
@Configuration
public class MyAutoConfiguration {
    static {
        System.out.println("MyAutoConfiguration load ...");
    }

    @Bean
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }
}
