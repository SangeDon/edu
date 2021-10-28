package cn.sangedon.rpc.consumer.config;

import cn.sangedon.rpc.consumer.interceptor.RpcDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc 拦截器配置
 *
 * @author dongliangqiong 2021-10-25 09:58
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public RpcDispatcher getRpcDispatcher() {
        return new RpcDispatcher();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截user下的所有访问请求
        String[] addPathPatterns = {
            "/user/**"
        };

        // 传入自定义的拦截器，以及拦截地址
        registry.addInterceptor(getRpcDispatcher()).addPathPatterns(addPathPatterns);
    }
}
