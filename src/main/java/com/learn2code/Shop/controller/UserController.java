package com.learn2code.Shop.controller;


import com.learn2code.Shop.db.service.impl.UserDAOService;
import com.learn2code.Shop.db.service.impl.UserRepository;
import com.learn2code.Shop.domain.Customer;
import com.learn2code.Shop.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user" )
public class UserController {

    private final UserDAOService userDAOService;
    private final UserRepository userRepository;


    public UserController(UserDAOService userDAOService, UserRepository userRepository) {
        this.userDAOService = userDAOService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody User user){ //vytiahne Body do objektu
        Integer id = userDAOService.insert(user);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); //ak sa nepodarí vytvoriť
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Customer customer = userDAOService.get(id);
        if (customer == null) { // ak nenajde zakaznika v db, vracia null a status not found
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
