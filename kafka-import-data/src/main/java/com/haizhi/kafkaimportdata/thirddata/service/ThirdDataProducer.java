package com.haizhi.kafkaimportdata.thirddata.service;

import com.alibaba.fastjson.JSONObject;
import com.haizhi.kafkaimportdata.thirddata.config.ChildrenField;
import com.haizhi.kafkaimportdata.thirddata.config.DataConfig;
import com.haizhi.kafkaimportdata.thirddata.repo.DataRepo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 生产者
 *
 * @author dongliangqiong 2021-11-02 15:06
 */
@Component
public class ThirdDataProducer {
    @Autowired
    private DataRepo dataRepo;

    @Autowired
    private DataConfig dataConfig;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void run() {
        String mainSql = dataConfig.getMainSql();
        List<Map<String, Object>> mainMap = dataRepo.executeSql(mainSql);
        List<ThirdCommitFormData> list = new ArrayList<>();
        for (Map<String, Object> map : mainMap) {
            List<ChildrenField> childrenFields = dataConfig.getChildrenFields();
            if (childrenFields != null && childrenFields.size() > 0) {
                for (ChildrenField childrenField : childrenFields) {
                    handleChildForm(map, childrenField);
                }
            }
            ThirdCommitFormData thirdCommitFormData = new ThirdCommitFormData();
            thirdCommitFormData.setFieldMap(map);
            thirdCommitFormData.setFormId(dataConfig.getFormId());
            thirdCommitFormData.setPoliceId(dataConfig.getPoliceId());
            list.add(thirdCommitFormData);
        }
        for (int i = 0; i < list.size(); i++) {
            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(dataConfig.getTopic(), Integer.toString(i),
                                                                                 JSONObject.toJSONString(list.get(i)));
            kafkaTemplate.send(producerRecord);
            System.out.println("消息发送成功");
        }
        System.out.println("消息全部发送成功");
    }

    private void handleChildForm(Map<String, Object> map, ChildrenField childrenField) {
        String sql = childrenField.getSql();
        if (!CollectionUtils.isEmpty(childrenField.getUnionKey())) {
            Map<String, String> unionKey = childrenField.getUnionKey();
            sql += " where ";
            for (Entry<String, String> entry : unionKey.entrySet()) {
                Object fieldValue = map.get(entry.getValue());
                sql = fillChildrenSql(sql, entry.getKey(), fieldValue);
            }
        }
        sql = clearSql(sql);
        if (!sql.contains("where")) {
            return;
        }
        List<Map<String, Object>> maps = dataRepo.executeSql(sql);
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("form_id", childrenField.getFormId());
        hashMap.put("field_map", maps);
        map.put(childrenField.getFieldId(), hashMap);
    }

    private String clearSql(String sql) {
        sql = sql.trim();

        if (sql.endsWith("and")) {
            sql = sql.substring(0, sql.lastIndexOf("and")).trim();
        }

        if (sql.endsWith("where")) {
            sql = sql.substring(0, sql.lastIndexOf("where")).trim();
        }

        return sql;
    }

    private String fillChildrenSql(String sql, String field, Object fieldValue) {
        if (fieldValue == null) {
            return sql;
        }
        if (fieldValue instanceof String) {
            String[] values = ((String) fieldValue).split(",");
            if (values != null && values.length > 1) {
                sql += field + " in ('" + String.join("','", values) + "') and";
            } else {
                sql += field + "='" + fieldValue + "' and";
            }
        }
        return sql;
    }
}
