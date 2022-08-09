package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.repository.StatueRepository;
import com.learn2code.Shop.db.repository.TruckRepository;
import com.learn2code.Shop.db.service.StatuesConsumer;
import com.learn2code.Shop.domain.Statue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("statue")
public class StatueController implements ControllerForAll<Statue> {

    private final TruckRepository truckRepository;
    private final StatueRepository statueRepository;

    public StatueController(TruckRepository truckRepository, StatueRepository statueRepository) {
        this.truckRepository = truckRepository;
        this.statueRepository = statueRepository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Statue statue) { //vytiahne Body do objektu
        statueRepository.save(statue);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //ak sa nepodarí vytvoriť
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

    @PostMapping("/saveStatues")
    public ResponseEntity<String> saveUsersTest(@RequestBody List<Statue> statues) {
        statueRepository.saveAll(statues);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //produkovanie dát do kafky
    @Autowired
    KafkaTemplate<String, Statue> kafkaTemplate;

    private static final String TOPIC = "demo";

    @PostMapping("/publishStatues")
    public ResponseEntity<String> publishMessage(@RequestBody List<Statue> statues) {

        for (Statue statue : statues) {
            kafkaTemplate.send(TOPIC, statue);
            System.out.println("published statue " + statue);
        }

        StatuesConsumer statuesConsumer = new StatuesConsumer(statueRepository, truckRepository, kafkaTemplate);
        statuesConsumer.consumeStatues();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
