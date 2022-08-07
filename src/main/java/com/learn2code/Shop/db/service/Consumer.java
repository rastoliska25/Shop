package com.learn2code.Shop.db.service;

import com.learn2code.Shop.db.repository.StatueRepository;
import com.learn2code.Shop.db.repository.UserRepository;
import com.learn2code.Shop.domain.Statue;
import com.learn2code.Shop.domain.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    
    private final StatueRepository statueRepository;

    public Consumer(StatueRepository statueRepository) {
        this.statueRepository = statueRepository;
    }

    @KafkaListener(topics = "demo", groupId = "mygroup")
    public void consumeStatuesFromTopic(Statue statue) {
        System.out.println("Consummed statue " + statue);
        statueRepository.save(statue);
    }
}
