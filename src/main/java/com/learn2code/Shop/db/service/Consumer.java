package com.learn2code.Shop.db.service;

import com.learn2code.Shop.db.repository.UserRepository;
import com.learn2code.Shop.domain.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    private final UserRepository userRepository;

    public Consumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "demo", groupId = "mygroup")
    public void consumeUserFromTopic(User user) {
        System.out.println("Consummed user " + user);
        userRepository.save(user);

    }
}
