package cn.sangedon.rpc.consumer.processor;

import cn.sangedon.rpc.consumer.util.RpcServerAutowiredUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Bean初始化后置处理器
 *
 * @author dongliangqiong 2021-10-24 19:31
 */
@Component
public class RpcAutowiredPostProcessor implements BeanPostProcessor {
    @Autowired
    private RpcServerAutowiredUtil rpcServerAutowiredUtil;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            rpcServerAutowiredUtil.rpcAutowired(bean);
            return bean;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
