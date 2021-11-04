package com.haizhi.kafkaimportdata.thirddata.repo;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author dongliangqiong 2021-11-02 15:41
 */
@Repository
public class DataRepo {
    @Autowired
    private JdbcTemplate template;

    public List<Map<String, Object>> executeSql(String sql) {
        return template.queryForList(sql);
    }
}
