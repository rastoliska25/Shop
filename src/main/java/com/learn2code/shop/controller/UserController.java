package com.learn2code.shop.controller;

import com.learn2code.shop.db.repository.UserRepository;
import com.learn2code.shop.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController implements ControllerForAll<User> {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody User user) { //vytiahne Body do objektu
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
}
