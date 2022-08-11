package com.learn2code.shop.controller;

import com.learn2code.shop.db.repository.CustomerRepository;
import com.learn2code.shop.domain.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customer")
public class CustomerController implements ControllerForAll<Customer> {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Customer customer) { //vytiahne Body do objektu
        customerRepository.save(customer);
        Integer id = customer.getId();
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); //ak sa nepodarí vytvoriť
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Optional<Customer> customerWithId = customerRepository.findById(id);
        if (customerWithId.isEmpty()) { // ak nenajde usera v db, vracia null a status not found
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerWithId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Customer> customers = customerRepository.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK); //vracia customerlist a status
    }
}
