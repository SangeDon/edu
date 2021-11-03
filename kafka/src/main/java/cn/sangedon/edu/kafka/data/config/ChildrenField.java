package cn.sangedon.edu.kafka.data.config;

import lombok.Data;
import lombok.ToString;

/**
 * 子表字段及对应的sql
 *
 * @author dongliangqiong 2021-11-02 15:50
 */
@Data
@ToString
public class ChildrenField {
    private String fieldId;

    private String sql;
}
