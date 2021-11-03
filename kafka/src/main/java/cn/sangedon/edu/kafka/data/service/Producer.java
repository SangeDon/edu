package cn.sangedon.edu.kafka.data.service;

import cn.sangedon.edu.kafka.data.config.ChildrenField;
import cn.sangedon.edu.kafka.data.config.DataConfig;
import cn.sangedon.edu.kafka.data.repo.DataRepo;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 生产者
 *
 * @author dongliangqiong 2021-11-02 15:06
 */
@Component
public class Producer {
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
            for (ChildrenField childrenField : childrenFields) {
                List<Map<String, Object>> maps = dataRepo.executeSql(childrenField.getSql());
                map.put(childrenField.getFieldId(), maps);
            }
            ThirdCommitFormData thirdCommitFormData = new ThirdCommitFormData();
            thirdCommitFormData.setFieldMap(map);
            list.add(thirdCommitFormData);
        }
        System.out.println("aaaaa");
        for (ThirdCommitFormData thirdCommitFormData : list) {
            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>("cache-message-single-sange", Integer.toString(0),
                                                                                 JSONObject.toJSONString(thirdCommitFormData));
            kafkaTemplate.send(producerRecord);
            System.out.println("消息发送成功");
        }
        System.out.println("消息全部发送成功");
    }
}
