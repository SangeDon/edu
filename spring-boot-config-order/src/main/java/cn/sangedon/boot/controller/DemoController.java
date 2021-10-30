package cn.sangedon.boot.controller;

import cn.sangedon.boot.config.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-26 20:26
 */
@RestController
@RequestMapping("demo")
public class DemoController {
    @Autowired
    private UserConfig userConfig;

    @GetMapping("index")
    public String getIndex() {
        return userConfig.toString();
    }
}
