package cn.sangedon.rpc.consumer;

import cn.sangedon.rpc.consumer.proxy.RpcClientProxy;
import cn.sangedon.rpc.service.UserService;
import cn.sangedon.rpc.service.pojo.User;

/**
 * 客户端启动类
 *
 * @author dongliangqiong 2021-10-23 15:04
 */
public class ClientBootStrap {
    public static void main(String[] args) {
        UserService userService = (UserService) RpcClientProxy.createProxy(UserService.class);
        User user = userService.getUserById(1);
        System.out.println(user);
    }
}
