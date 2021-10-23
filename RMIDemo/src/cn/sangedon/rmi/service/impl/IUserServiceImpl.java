package cn.sangedon.rmi.service.impl;

import cn.sangedon.rmi.pojo.User;
import cn.sangedon.rmi.service.IUserService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongliangqiong 2021-10-23 10:16
 * @Description TODO
 */
public class IUserServiceImpl extends UnicastRemoteObject implements IUserService {
    Map<Integer, User> userMap = new HashMap<>();

    public IUserServiceImpl() throws RemoteException {
        super();
        User user1 = new User(1, "张三");
        User user2 = new User(2, "李四");
        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
    }

    @Override
    public User getUserById(int id) throws RemoteException {
        return userMap.get(id);
    }
}
