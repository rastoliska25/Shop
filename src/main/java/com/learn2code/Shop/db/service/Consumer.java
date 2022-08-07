package com.learn2code.Shop.db.service;

import com.learn2code.Shop.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    /*
    @KafkaListener(topics="test", groupId="mygroup")
    public void consumeFromTopic(String message) {
        System.out.println("Consummed message "+message);

    }

     */


    @KafkaListener(topics="demo", groupId="mygroup")
    public void consumeUserFromTopic(User user) {
        System.out.println("Consummed user "+user);
    }



}
