package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.repository.UserRepository;
import com.learn2code.Shop.db.service.Consumer;
import com.learn2code.Shop.db.service.Producer;
import com.learn2code.Shop.db.service.api.UserService;
import com.learn2code.Shop.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController implements UserService {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody User user) { //vytiahne Body do objektu
        userRepository.save(user);
        Integer id = user.getId();
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Optional<User> userWithId = userRepository.findById(id);
        if (userWithId.isEmpty()) { // ak nenajde usera v db, vracia null a status not found
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userWithId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK); //vracia userlist a status
    }

    @PostMapping("/saveUsersTest")
    public ResponseEntity<String> insertUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("James", "Gosling"));
        users.add(new User("Doug", "Lea"));
        users.add(new User("Martin", "Fowler"));
        users.add(new User("Brian", "Goetz"));
        userRepository.saveAll(users);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/saveUsers")
    public ResponseEntity<String> saveUsersTest(@RequestBody List<User> users) {
        userRepository.saveAll(users);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    //list userov
    /*
    @Autowired
    KafkaTemplate<String,List<User>> kafkaTemplate;

    private static final String TOPIC = "demo";

    @PostMapping("/publish")
    public String publishMessage(@RequestBody List<User> users)
    {
        kafkaTemplate.send(TOPIC, users);
        System.out.println("published user" + users);
        return "Published Successfully!";
    }
    */

    @Autowired
    KafkaTemplate<String,User> kafkaTemplate;

    private static final String TOPIC = "demo";

    @PostMapping("/publish")
    public String publishMessage(@RequestBody User user)
    {
        kafkaTemplate.send(TOPIC, user);
        System.out.println("published user" + user);
        return "Published Successfully!";
    }




}
