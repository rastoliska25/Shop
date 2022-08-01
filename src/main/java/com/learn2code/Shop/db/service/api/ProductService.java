package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.domain.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    public ResponseEntity add(Product product);

    public ResponseEntity get(int id);

    public ResponseEntity getAll();
}
