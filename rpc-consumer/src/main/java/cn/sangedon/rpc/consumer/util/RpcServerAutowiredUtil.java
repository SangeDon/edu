package cn.sangedon.rpc.consumer.util;

import cn.sangedon.rpc.consumer.anno.RpcAutowired;
import cn.sangedon.rpc.consumer.config.RpcHost;
import cn.sangedon.rpc.consumer.config.RpcHostConfig;
import cn.sangedon.rpc.consumer.proxy.RpcClientProxy;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Rpc 服务自动注入工具类，轮训注入
 *
 * @author dongliangqiong 2021-10-25 10:06
 */
@Component
public class RpcServerAutowiredUtil {
    private AtomicInteger index = new AtomicInteger();

    @Autowired
    private RpcHostConfig rpcHostConfig;

    public Object rpcAutowired(Object bean) throws Exception {
        Class<?> beanClass = bean.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(RpcAutowired.class)) {
                Object proxy = RpcClientProxy.createProxy(field.getType(), getCurrentServer());
                field.setAccessible(true);
                field.set(bean, proxy);
            }
        }
        return bean;
    }

    private RpcHost getCurrentServer() {
        int size = rpcHostConfig.getRpcHosts().size();
        int nextIndex = (index.addAndGet(1) % size);
        index.set(nextIndex);
        return rpcHostConfig.getRpcHosts().get(index.get());
    }
}
