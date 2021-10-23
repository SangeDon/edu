package cn.sangedon.rpc.consumer.proxy;

import cn.sangedon.rpc.consumer.client.RpcClient;
import cn.sangedon.rpc.service.common.RpcRequest;
import cn.sangedon.rpc.service.common.RpcResponse;
import com.alibaba.fastjson.JSON;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * Rpc 客户端代理对象
 *
 * @author dongliangqiong 2021-10-23 14:53
 */
public class RpcClientProxy {
    public static Object createProxy(Class serviceClass) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {serviceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.setRequestId(UUID.randomUUID().toString());
                rpcRequest.setClassName(method.getDeclaringClass().getName());
                rpcRequest.setMethodName(method.getName());
                rpcRequest.setParameterTypes(method.getParameterTypes());
                rpcRequest.setParameters(args);
                RpcClient rpcClient = null;
                try {
                    rpcClient = new RpcClient("127.0.0.1", 9999);
                    Object responseMsg = rpcClient.send(JSON.toJSONString(rpcRequest));
                    RpcResponse rpcResponse = JSON.parseObject(responseMsg.toString(), RpcResponse.class);
                    if (rpcResponse.getError() != null) {
                        throw new RuntimeException(rpcResponse.getError());
                    }
                    Object result = rpcResponse.getResult();
                    return JSON.parseObject(result.toString(), method.getReturnType());
                } catch (Exception e) {
                    throw e;
                } finally {
                    rpcClient.close();
                }
            }
        });
    }
}
