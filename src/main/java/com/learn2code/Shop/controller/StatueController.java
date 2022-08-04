package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.repository.StatueRepository;
import com.learn2code.Shop.db.service.api.StatueService;
import com.learn2code.Shop.domain.Statue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("statue")
public class StatueController implements StatueService {

    private final StatueRepository statueRepository;

    public StatueController(StatueRepository statueRepository) {
        this.statueRepository = statueRepository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Statue statue) { //vytiahne Body do objektu
        statueRepository.save(statue);
        Integer id = statue.getId();
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); //ak sa nepodarí vytvoriť
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        Optional<Statue> statueWithId = statueRepository.findById(id);
        if (statueWithId.isEmpty()) { // ak nenajde statue v db, vracia null a status not found
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(statueWithId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<Statue> statues = statueRepository.findAll();
        return new ResponseEntity<>(statues, HttpStatus.OK); //vracia statuelist a status
    }

    @GetMapping("/filterName/{name}")
    public List<Statue> getInformation(@PathVariable("name") String name) {
        return statueRepository.getInformation(name);
    }

    @GetMapping("/loadSecuring/{loadSecuring}")
    public List<Statue> getJoinInformation(@PathVariable("loadSecuring") Integer loadSecuring) {
        return statueRepository.getJoinInformation(loadSecuring);
    }

    @GetMapping("/findByStatue/{firstName}")
    public List<Statue> findByName(@PathVariable("firstName") String firstName) {
        return statueRepository.findByName(firstName);
    }
}
