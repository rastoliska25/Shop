package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.domain.Truck;
import org.springframework.http.ResponseEntity;

public interface TruckService {

    public ResponseEntity add(Truck truck);

    public ResponseEntity get(int id);

    public ResponseEntity getAll();
}
