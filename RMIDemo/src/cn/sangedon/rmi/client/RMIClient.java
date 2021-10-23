package cn.sangedon.rmi.client;

import cn.sangedon.rmi.pojo.User;
import cn.sangedon.rmi.service.IUserService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author dongliangqiong 2021-10-23 10:24
 * @Description TODO
 */
public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9998);
            IUserService userService = (IUserService) registry.lookup("userService");
            User userById = userService.getUserById(1);
            System.out.println("用户信息：" + userById);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
