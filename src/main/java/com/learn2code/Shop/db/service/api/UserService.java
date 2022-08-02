package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.domain.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity add(User user);

    public ResponseEntity get(int id);

    public ResponseEntity getAll();
}