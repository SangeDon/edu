package cn.sangedon.springboot.controller;

import cn.sangedon.springboot.mvc.annotation.Controller;
import cn.sangedon.springboot.mvc.annotation.RequestMapping;

/**
 * @author dongliangqiong 2021-09-09 17:58
 */
@Controller
public class DemoController {

    @RequestMapping("/hello")
    public String demo() {
        return "hello";
    }
}
