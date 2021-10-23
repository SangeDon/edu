package cn.sangedon.rpc.provider.service;

import cn.sangedon.rpc.provider.anno.RpcService;
import cn.sangedon.rpc.service.UserService;
import cn.sangedon.rpc.service.pojo.User;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author dongliangqiong 2021-10-23 11:37
 * @Description TODO
 */
@RpcService
@Service
public class UserServiceImpl implements UserService {
    Map<Integer, User> userMap = new HashMap<>();

    public UserServiceImpl() throws RemoteException {
        super();
        User user1 = new User(1, "张三");
        User user2 = new User(2, "李四");
        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
    }

    @Override
    public User getUserById(int id) {
        return userMap.get(id);
    }
}
