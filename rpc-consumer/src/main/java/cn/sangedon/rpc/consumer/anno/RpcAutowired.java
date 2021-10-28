package cn.sangedon.rpc.consumer.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rpc 服务注入标志注解
 *
 * @author dongliangqiong 2021-10-24 19:30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcAutowired {
}
