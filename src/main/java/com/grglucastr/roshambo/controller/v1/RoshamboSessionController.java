package com.grglucastr.roshambo.controller.v1;

import com.grglucastr.roshambo.model.GameResult;
import com.grglucastr.roshambo.model.GameStatus;
import com.grglucastr.roshambo.model.Player;
import com.grglucastr.roshambo.model.RoshamboSession;
import com.grglucastr.roshambo.repository.v1.RoshamboSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @GetMapping(value = "/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoshamboSession> fingSessionById(@PathVariable("sessionId")Integer id){
        Optional<RoshamboSession> optionalRoshamboSession = roshamboRepository.findById(id);

        return optionalRoshamboSession.map(session -> ResponseEntity.ok(session))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoshamboSession> createSession(@RequestBody RoshamboSession obj){
        RoshamboSession session = roshamboRepository.add(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @DeleteMapping(value = "/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteSession(@PathVariable("sessionId")Integer id){
        Optional<RoshamboSession> optionalRoshamboSession = roshamboRepository.findById(id);

        if(optionalRoshamboSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        roshamboRepository.remove(optionalRoshamboSession.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "{sessionId}/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameStatus> startGame(@PathVariable("sessionId") Integer sessionId){
        Optional<RoshamboSession> optSession = roshamboRepository.findById(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        optSession.get().start();

        return ResponseEntity.ok(optSession.get().getGameStatus());
    }


    @GetMapping(value = "{sessionId}/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameResult> gameResults(@PathVariable("sessionId") Integer sessionId){
        Optional<RoshamboSession> optSession = roshamboRepository.findById(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(optSession.get().getGameResult());
    }

    @GetMapping(value = "{sessionId}/winner", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> gameWinner(@PathVariable("sessionId") Integer sessionId){
        Optional<RoshamboSession> optSession = roshamboRepository.findById(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(optSession.get().getWinner());
    }

    @GetMapping(value = "{sessionId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameStatus> gameStatus(@PathVariable("sessionId") Integer sessionId){
        Optional<RoshamboSession> optSession = roshamboRepository.findById(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(optSession.get().getGameStatus());
    }

    @GetMapping(value = "{sessionId}/eliminations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Player, List<Player>>> eliminations(@PathVariable("sessionId") Integer sessionId){
        Optional<RoshamboSession> optSession = roshamboRepository.findById(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(optSession.get().getBeats());
    }

}
