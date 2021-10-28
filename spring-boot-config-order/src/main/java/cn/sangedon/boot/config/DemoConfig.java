package cn.sangedon.boot.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-26 20:23
 */
@ToString
@Data
@Component
public class DemoConfig extends PropertySourcesPlaceholderConfigurer implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
        try {
            String path = environment.getProperty("config.path");
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            Properties props = new Properties();
            props.load(fileInputStream);
            PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
            propertySourcesPlaceholderConfigurer.setProperties(props);
            return propertySourcesPlaceholderConfigurer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
