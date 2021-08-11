package cn.sangedon.edu.demo.service.impl;

import cn.sangedon.edu.demo.service.DemoService;
import cn.sangedon.edu.mvc.framework.annotation.SangedonService;

/**
 * @author dongliangqiong
 */
@SangedonService
public class DemoServiceImpl implements DemoService {
    @Override
    public String getByName(String name) {
        return name;
    }
}
