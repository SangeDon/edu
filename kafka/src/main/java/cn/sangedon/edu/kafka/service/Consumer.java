package cn.sangedon.edu.kafka.service;

import cn.sangedon.edu.kafka.config.ThirdDataImportConfig;
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

    @KafkaListener(topicPattern = "cache-message-.*")
    public void ackListener(ConsumerRecord consumerRecord, Acknowledgment ack) throws InterruptedException {
        System.out.println("sum: " + sum.incrementAndGet());

        Optional.ofNullable(consumerRecord.value()).ifPresent(record -> {
            System.out.println(">>>>>>>>>> record = " + record);
        });
        ack.acknowledge();
    }
}
