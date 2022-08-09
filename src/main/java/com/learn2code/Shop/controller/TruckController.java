package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.repository.TruckRepository;
import com.learn2code.Shop.domain.Truck;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("truck")
public class TruckController implements ControllerForAll<Truck> {

    private final TruckRepository truckRepository;

    public TruckController(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Truck truck) { //vytiahne Body do objektu
        truckRepository.save(truck);
        Integer id = truck.getId();
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); //ak sa nepodarí vytvoriť
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Optional<Truck> truckWithId = truckRepository.findById(id);
        if (truckWithId.isEmpty()) { // ak nenajde truck v db, vracia null a status not found
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(truckWithId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Truck> trucks = truckRepository.findAll();
        return new ResponseEntity<>(trucks, HttpStatus.OK); //vracia trucklist a status
    }

}
