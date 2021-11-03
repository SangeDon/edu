package cn.sangedon.edu.kafka.data.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 三方数据导入配置
 *
 * @author dongliangqiong 2021-11-02 14:47
 */
@Data
@Component
@ConfigurationProperties(prefix = "data")
public class DataConfig {
    private String mainSql;

    private List<String> fieldList;

    private List<ChildrenField> childrenFields;

    private Integer from;

    private Integer to;
}
