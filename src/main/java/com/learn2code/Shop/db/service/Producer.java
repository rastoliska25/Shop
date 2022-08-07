package com.learn2code.Shop.db.service;

import com.learn2code.Shop.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
public class Producer {
    public static final String topic = "test";

    @Autowired
    private KafkaTemplate<String, User> kafkaTemp;

    /*
    public void publishToTopic(String message) {
        System.out.println("Publishing to topic "+topic);
        this.kafkaTemp.send(topic, message);
    }
    */


    public void publishUserToTopic(User user) {
        System.out.println("Publishing to topic "+topic);
        this.kafkaTemp.send(topic, user);
    }
}
