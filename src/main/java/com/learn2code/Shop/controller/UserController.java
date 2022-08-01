package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.repository.UserRepository;
import com.learn2code.Shop.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody User user) { //vytiahne Body do objektu
        userRepository.save(user);
        Integer id = user.getId();
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); //ak sa nepodarí vytvoriť
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
}
