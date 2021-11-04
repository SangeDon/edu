package com.haizhi.kafkaimportdata.thirddata.config;

import java.util.Map;
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
    /**
     * 子表ID
     */
    private String formId;

    /**
     * 子表字段ID
     */
    private String fieldId;

    /**
     * 子表数据查询sql
     */
    private String sql;

    /**
     * 子表和主表关联字段
     */
    private Map<String, String> unionKey;
}
