package cn.sangedon.rpc.consumer.interceptor;

import cn.sangedon.rpc.consumer.util.RpcServerAutowiredUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Rpc 服务调用转发 每次调用后，将更换注入的服务端服务
 *
 * @author dongliangqiong 2021-10-25 09:52
 */
@Component
public class RpcDispatcher implements HandlerInterceptor {
    @Autowired
    private RpcServerAutowiredUtil rpcServerAutowiredUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Object bean = ((HandlerMethod) handler).getBean();
        rpcServerAutowiredUtil.rpcAutowired(bean);
    }
}
