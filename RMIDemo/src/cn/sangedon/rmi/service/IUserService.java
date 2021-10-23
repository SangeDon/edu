package cn.sangedon.rmi.service;

import cn.sangedon.rmi.pojo.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author dongliangqiong 2021-10-23 10:15
 * @Description TODO
 */
public interface IUserService extends Remote {
    User getUserById(int id) throws RemoteException;
}
