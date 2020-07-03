package com.grglucastr.roshambo.controller.v1;

import com.grglucastr.roshambo.model.Move;
import com.grglucastr.roshambo.model.Player;
import com.grglucastr.roshambo.model.Strategy;
import com.grglucastr.roshambo.repository.v1.MoveRepository;
import com.grglucastr.roshambo.repository.v1.PlayerRepository;
import com.grglucastr.roshambo.repository.v1.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class MoveController implements HTTPRequestable<Move> {

    private MoveRepository moveRepository;
    private StrategyRepository strategyRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public MoveController(
            MoveRepository moveRepository,
            StrategyRepository strategyRepository,
            PlayerRepository playerRepository) {
        this.moveRepository = moveRepository;
        this.strategyRepository = strategyRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    @GetMapping(value = "/moves", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Move>> listAll() {
        return ResponseEntity.ok(moveRepository.listAll());
    }

    @Override
    @GetMapping(value = "/moves/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Move> getSingleOne(@PathVariable("id") Integer id) {
        Optional<Move> move = moveRepository.findById(id);
        return move.map(m -> ResponseEntity.ok(m))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping(value = "/moves", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Move> addNew(@RequestBody Move newObj) {
        Optional<Player> optionalPlayer = playerRepository.findById(newObj.getPlayer().getId());
        Optional<Strategy> optionalStrategy = strategyRepository.findById(newObj.getStrategy().getId());

        if(optionalPlayer.isEmpty() || optionalStrategy.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        newObj.setStrategy(optionalStrategy.get());
        newObj.setPlayer(optionalPlayer.get());

        Move move = moveRepository.add(newObj);
        return ResponseEntity.status(HttpStatus.CREATED).body(move);
    }

    @Override
    @PutMapping(value = "/moves/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Move> update(@PathVariable("id") Integer id, Move obj) {
        Optional<Player> optionalPlayer = playerRepository.findById(obj.getPlayer().getId());
        Optional<Strategy> optionalStrategy = strategyRepository.findById(obj.getStrategy().getId());
        Optional<Move> optionalMove = moveRepository.findById(id);

        if(optionalPlayer.isEmpty() || optionalStrategy.isEmpty() || optionalMove.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Move updateMove = optionalMove.get();
        updateMove.setStrategy(optionalStrategy.get());
        updateMove.setPlayer(optionalPlayer.get());

        moveRepository.update(updateMove);

        return ResponseEntity.ok(updateMove);
    }

    @Override
    @DeleteMapping(value = "/moves/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        Optional<Move> optionalMove = moveRepository.findById(id);

        if(optionalMove.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        moveRepository.remove(optionalMove.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping(value = "/players/{playerId}/moves", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Move> getMoveByPlayerId(@PathVariable("playerId") Integer playerId){
        Optional<Move> optionalMove = moveRepository.findByPlayerId(playerId);
        return optionalMove.map(m -> ResponseEntity.ok(m))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/players/{playerId}/moves")
    public ResponseEntity deleteByPlayerId(@PathVariable("playerId") Integer playerId){
        Optional<Move> optionalMove = moveRepository.findByPlayerId(playerId);

        if(optionalMove.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        moveRepository.remove(optionalMove.get());
        return ResponseEntity.noContent().build();
    }

}
