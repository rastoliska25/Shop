package com.learn2code.Shop.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    @KafkaListener(topics="mytopic", groupId="mygroup")
    public void consumeFromTopic(String message) {
        System.out.println("Consummed message "+message);
    }
}
