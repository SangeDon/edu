package cn.sangedon.springboot.boot;

import cn.sangedon.springboot.mvc.DispatcherServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;

/**
 * @author dongliangqiong 2021-09-09 11:44
 */
public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ApplicationContext ac = new ApplicationContext();
        ac.register(AppConfig.class);
        ac.refresh();

        DispatcherServlet servlet = new DispatcherServlet(ac);
        ServletRegistration.Dynamic registration = servletContext.addServlet("/", servlet);
        if (registration != null) {
            registration.setLoadOnStartup(1);
            registration.addMapping("/*");
        }
    }
}
