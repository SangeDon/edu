package cn.sangedon.edu.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 三方数据导入的配置类
 *
 * @author dongliangqiong 2021-10-21 20:05
 */
@Configuration
@ConfigurationProperties(prefix = "thirddata")
@Data
public class ThirdDataImportConfig {
    /**
     * 是否开启kafka数据同步
     */
    private boolean enable;

    /**
     * 警号
     */
    private String policeId;
}
