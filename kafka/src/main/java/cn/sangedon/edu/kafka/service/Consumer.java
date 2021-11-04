package cn.sangedon.edu.kafka.service;

import cn.sangedon.edu.kafka.config.ThirdDataImportConfig;
import cn.sangedon.edu.kafka.data.config.BeanUtil;
import cn.sangedon.edu.kafka.data.service.ThirdCommitFormData;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author dongliangqiong 2021-10-19 18:11
 * @Description TODO
 */
@Component
public class Consumer {

    @Autowired
    private ThirdDataImportConfig thirdDataImportConfig;

    private AtomicInteger sum = new AtomicInteger();

    @KafkaListener(topicPattern = "cache-message-test-.*")
    public void ackListener(ConsumerRecord consumerRecord, Acknowledgment ack) throws InterruptedException, IOException {
        System.out.println("sum: " + sum.incrementAndGet());
        Map<String, Object> fieldMap = BeanUtil.beanToMap(JSONObject.parse((String) consumerRecord.value()));
        ThirdCommitFormData thirdCommitFormData = BeanUtil.mapToBean(fieldMap, ThirdCommitFormData.class);
        System.out.println(thirdCommitFormData);
        ack.acknowledge();
    }

    @KafkaListener(topicPattern = "cache-message-quick-.*")
    public void ackListenerList(ConsumerRecord consumerRecord, Acknowledgment ack) throws InterruptedException, IOException {
        System.out.println("sum: " + sum.incrementAndGet());
        Map<String, Object> fieldMap = BeanUtil.beanToMap(JSONObject.parse((String) consumerRecord.value()));
        Optional.ofNullable(consumerRecord.value()).ifPresent(record -> {
            System.out.println(">>>>>>>>>> record = " + record);
        });
        ack.acknowledge();
    }
}
