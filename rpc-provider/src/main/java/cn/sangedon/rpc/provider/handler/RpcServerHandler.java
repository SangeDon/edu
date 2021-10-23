package cn.sangedon.rpc.provider.handler;

import cn.sangedon.rpc.provider.anno.RpcService;
import cn.sangedon.rpc.service.common.RpcRequest;
import cn.sangedon.rpc.service.common.RpcResponse;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Rpc请求处理
 *
 * @author dongliangqiong 2021-10-23 12:16
 */
@Sharable
@Service
public class RpcServerHandler extends SimpleChannelInboundHandler<String> implements ApplicationContextAware {
    private static final Map SERVICE_INSTANCE_MAP = new ConcurrentHashMap();

    /**
     * 通道读取就绪事件
     *
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        // 接受客户端请求 - 将message转换为RpcRequest对象
        RpcRequest rpcRequest = JSON.parseObject(s, RpcRequest.class);
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        try {
            rpcResponse.setResult(handler(rpcRequest));
        } catch (Exception e) {
            rpcResponse.setError(e.getMessage());
        }
        channelHandlerContext.writeAndFlush(JSON.toJSONString(rpcResponse));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (!CollectionUtils.isEmpty(serviceMap)) {
            Set<Entry<String, Object>> entries = serviceMap.entrySet();
            for (Entry<String, Object> entry : entries) {
                Object value = entry.getValue();
                if (value.getClass().getInterfaces().length == 0) {
                    throw new RuntimeException("远程服务必须实现接口");
                }

                SERVICE_INSTANCE_MAP.put(value.getClass().getInterfaces()[0].getName(), value);
            }
        }
    }

    private Object handler(RpcRequest rpcRequest) throws InvocationTargetException {
        Object serverBean = SERVICE_INSTANCE_MAP.get(rpcRequest.getClassName());
        if (serverBean == null) {
            throw new RuntimeException("");
        }
        Class<?> aClass = serverBean.getClass();
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();
        // CGLIB反射调用
        FastClass fastClass = FastClass.create(aClass);
        FastMethod method = fastClass.getMethod(methodName, parameterTypes);
        return method.invoke(serverBean, parameters);
    }
}
