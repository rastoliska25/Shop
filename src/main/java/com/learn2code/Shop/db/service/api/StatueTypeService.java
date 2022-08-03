package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.domain.StatueType;
import org.springframework.http.ResponseEntity;

public interface StatueTypeService {

    public ResponseEntity add(StatueType statueType);

    public ResponseEntity get(int id);

    public ResponseEntity getAll();
}
