package com.haizhi.kafkaimportdata;

import com.haizhi.kafkaimportdata.thirddata.service.ThirdDataProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaImportDataApplication  implements CommandLineRunner {

    @Autowired
    private ThirdDataProducer thirdDataProducer;

    public static void main(String[] args) {
        SpringApplication.run(KafkaImportDataApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        thirdDataProducer.run();
    }
}
