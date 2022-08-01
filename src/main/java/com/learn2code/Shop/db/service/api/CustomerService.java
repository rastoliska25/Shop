package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.domain.Customer;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    public ResponseEntity add(Customer customer);

    public ResponseEntity get(int id);

    public ResponseEntity getAll();
}
