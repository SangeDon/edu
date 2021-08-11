package cn.sangedon.edu.demo.controller;

import cn.sangedon.edu.demo.service.DemoService;
import cn.sangedon.edu.mvc.framework.annotation.SangedonAutowired;
import cn.sangedon.edu.mvc.framework.annotation.SangedonController;
import cn.sangedon.edu.mvc.framework.annotation.SangedonRequestMapping;

/**
 * @author dongliangqiong
 */
@SangedonController
@SangedonRequestMapping("/demo")
public class DemoController {
    @SangedonAutowired
    private DemoService demoService;

    @SangedonRequestMapping("/query")
    public String query(String name) {
        System.out.println("sange enter demo controller:" + name);
        return demoService.getByName(name);
    }
}
