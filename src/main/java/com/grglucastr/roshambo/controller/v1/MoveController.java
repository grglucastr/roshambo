package com.grglucastr.roshambo.controller.v1;

import com.grglucastr.roshambo.model.Move;
import com.grglucastr.roshambo.model.RoshamboSession;
import com.grglucastr.roshambo.repository.v1.MoveRepository;
import com.grglucastr.roshambo.repository.v1.RoshamboSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/roshambo-sessions/{sessionId}/moves")
public class MoveController extends BaseController {

    private MoveRepository moveRepository;

    @Autowired
    public MoveController(RoshamboSessionRepository roshamboRepository) {
        super(roshamboRepository);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Move>> listSessionMoves(@PathVariable("sessionId") Integer sessionId){
        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optSession.get().getMoves());
    }

}
