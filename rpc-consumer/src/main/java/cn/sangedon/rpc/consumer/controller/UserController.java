package cn.sangedon.rpc.consumer.controller;

import cn.sangedon.rpc.consumer.anno.RpcAutowired;
import cn.sangedon.rpc.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息controller层
 */
@RestController
@RequestMapping("user")
//@Scope("prototype")
public class UserController {

    @RpcAutowired
    private UserService userService;

    @GetMapping("get_by_id")
    public String getUserById(@RequestParam("id") Integer id) {
        return userService.getUserById(id).toString();
    }
}
