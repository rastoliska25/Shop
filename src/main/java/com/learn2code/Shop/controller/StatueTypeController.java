package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.repository.StatueTypeRepository;
import com.learn2code.Shop.db.service.api.StatueTypeService;
import com.learn2code.Shop.domain.StatueType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("statueType")
public class StatueTypeController implements StatueTypeService {

    private final StatueTypeRepository statueTypeRepository;

    public StatueTypeController(StatueTypeRepository statueTypeRepository) {
        this.statueTypeRepository = statueTypeRepository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody StatueType statueType) { //vytiahne Body do objektu
        statueTypeRepository.save(statueType);
        Integer id = statueType.getId();
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); //ak sa nepodarí vytvoriť
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Optional<StatueType> statueTypeWithId = statueTypeRepository.findById(id);
        if (statueTypeWithId.isEmpty()) { // ak nenajde statueType v db, vracia null a status not found
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(statueTypeWithId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<StatueType> statueTypes = statueTypeRepository.findAll();
        return new ResponseEntity<>(statueTypes, HttpStatus.OK); //vracia statueTypelist a status
    }

    @GetMapping("/loadSecuring")
    public List<StatueType> getJoinInformation() {
        return statueTypeRepository.getJoinInformation();
    }

}
