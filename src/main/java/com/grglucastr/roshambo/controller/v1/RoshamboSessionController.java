package com.grglucastr.roshambo.controller.v1;

import com.grglucastr.roshambo.model.RoshamboSession;
import com.grglucastr.roshambo.repository.v1.RoshamboSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/roshambo-sessions")
public class RoshamboSessionController {

    private RoshamboSessionRepository roshamboRepository;

    @Autowired
    public RoshamboSessionController(RoshamboSessionRepository roshamboRepository) {
        this.roshamboRepository = roshamboRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoshamboSession>> listAllAvailableSessions(){
        List<RoshamboSession> lst = roshamboRepository.listAll();
        return ResponseEntity.ok(lst);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoshamboSession> fingSessionById(@PathVariable("id")Integer id){
        Optional<RoshamboSession> optionalRoshamboSession = roshamboRepository.findById(id);

        return optionalRoshamboSession.map(session -> ResponseEntity.ok(session))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoshamboSession> createSession(@RequestBody RoshamboSession obj){
        RoshamboSession session = roshamboRepository.add(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteSession(@PathVariable("id")Integer id){
        Optional<RoshamboSession> optionalRoshamboSession = roshamboRepository.findById(id);

        if(optionalRoshamboSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        roshamboRepository.remove(optionalRoshamboSession.get());
        return ResponseEntity.noContent().build();
    }

}
