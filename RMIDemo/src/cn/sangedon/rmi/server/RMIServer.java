package cn.sangedon.rmi.server;

import cn.sangedon.rmi.service.IUserService;
import cn.sangedon.rmi.service.impl.IUserServiceImpl;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author dongliangqiong 2021-10-23 10:19
 * @Description TODO
 */
public class RMIServer {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(9998);

            IUserService userService = new IUserServiceImpl();

            registry.rebind("userService", userService);
            System.out.println("RMI 服务端已启动！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
