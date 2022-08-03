package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.domain.Statue;
import org.springframework.http.ResponseEntity;

public interface StatueService {

    public ResponseEntity add(Statue statue);

    public ResponseEntity get(int id);

    public ResponseEntity getAll();

}
