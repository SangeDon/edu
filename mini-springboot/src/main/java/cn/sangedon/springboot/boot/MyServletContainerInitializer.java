package cn.sangedon.springboot.boot;

import java.util.Set;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

/**
 * @author dongliangqiong 2021-09-09 17:52
 */
@HandlesTypes(MyServletContainerInitializer.class)
public class MyServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        new ApplicationInitializer().onStartup(servletContext);
    }
}
