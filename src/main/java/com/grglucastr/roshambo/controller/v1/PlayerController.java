package com.grglucastr.roshambo.controller.v1;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.grglucastr.roshambo.model.Move;
import com.grglucastr.roshambo.model.Player;
import com.grglucastr.roshambo.model.RoshamboSession;
import com.grglucastr.roshambo.model.Strategy;
import com.grglucastr.roshambo.repository.v1.MoveRepository;
import com.grglucastr.roshambo.repository.v1.PlayerRepository;
import com.grglucastr.roshambo.repository.v1.RoshamboSessionRepository;
import com.grglucastr.roshambo.repository.v1.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/roshambo-sessions/{sessionId}/players")
public class PlayerController extends BaseController{

    private PlayerRepository playerRepository;
    private MoveRepository moveRepository;
    private StrategyRepository strategyRepository;

    @Autowired
    public PlayerController(RoshamboSessionRepository roshamboRepository) {
        super(roshamboRepository);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Player>>listAllPlayers(@PathVariable("sessionId") Integer sessionId){

        Optional<RoshamboSession> optSession = getSession(sessionId);

        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        optSession.get().getPlayers().forEach(player -> {
            addLinkToMoves(sessionId, player);
        });

        return ResponseEntity.ok(optSession.get().getPlayers());
    }

    @GetMapping(value = "/{playerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getPlayerByPlayerId(@PathVariable("sessionId") Integer sessionId,
                                                      @PathVariable("playerId") Integer playerId){
        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        playerRepository = new PlayerRepository(optSession.get().getPlayers());
        Optional<Player> optPlayer = playerRepository.findById(playerId);

        if(optPlayer.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        addLinkToMoves(sessionId, optPlayer.get());
        return ResponseEntity.ok(optPlayer.get());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> addNewPlayer(@PathVariable("sessionId") Integer sessionId,
                                               @RequestBody Player newPlayer){

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        playerRepository = new PlayerRepository(optSession.get().getPlayers());
        Player player = playerRepository.add(newPlayer);
        optSession.get().getPlayers().add(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(player);
    }

    @PutMapping(value = "/{playerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> update(@PathVariable("sessionId") Integer sessionId,
                                         @PathVariable("playerId") Integer id,
                                         @RequestBody Player player){

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        playerRepository = new PlayerRepository(optSession.get().getPlayers());
        Optional<Player> foundPlayer = playerRepository.findById(id);

        if(foundPlayer.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Player updatePlayer = foundPlayer.get();
        updatePlayer.setName(player.getName());

        playerRepository.update(updatePlayer);
        optSession.get().getPlayers().addAll(playerRepository.listAll());

        return ResponseEntity.ok(updatePlayer);
    }

    @DeleteMapping( value = "/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> deletePlayer(@PathVariable("sessionId") Integer sessionId,
                                               @PathVariable("playerId") Integer id){

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        playerRepository = new PlayerRepository(optSession.get().getPlayers());
        Optional<Player> foundPlayer = playerRepository.findById(id);

        if(foundPlayer.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        if(playerRepository.removeById(id)){
            optSession.get().getPlayers().addAll(playerRepository.listAll());
            removeMoveByPlayer(optSession.get(), foundPlayer.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "{playerId}/moves", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Move>> listAllPlayerMOve(@PathVariable("sessionId") Integer sessionId,
                                                       @PathVariable("playerId") Integer playerId){

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        playerRepository = new PlayerRepository(optSession.get().getPlayers());
        Optional<Player> foundPlayer = playerRepository.findById(playerId);

        if(foundPlayer.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        moveRepository = new MoveRepository(optSession.get().getMoves());
        Set<Move> playerMoves = moveRepository.findMovesByPlayerId(foundPlayer.get().getId());
        return ResponseEntity.ok(playerMoves);
    }

    @PostMapping(value = "{playerId}/moves", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Move> addPlayerMove(@PathVariable("sessionId") Integer sessionId,
                                              @PathVariable("playerId") Integer playerId,
                                              @RequestBody Strategy strategy){

        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        playerRepository = new PlayerRepository(optSession.get().getPlayers());
        Optional<Player> foundPlayer = playerRepository.findById(playerId);
        if(foundPlayer.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        strategyRepository = new StrategyRepository(optSession.get().getStrategies());
        Optional<Strategy> foundStrategy = strategyRepository.findById(strategy.getId());
        if(foundStrategy.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        moveRepository = new MoveRepository(optSession.get().getMoves());
        Move newMove = moveRepository.add(new Move(null, foundPlayer.get(), foundStrategy.get()));
        optSession.get().getMoves().add(newMove);

        return ResponseEntity.status(HttpStatus.CREATED).body(newMove);
    }


    @DeleteMapping(value = "{playerId}/moves", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Move> removePlayerMove(@PathVariable("sessionId") Integer sessionId,
                                                 @PathVariable("playerId") Integer playerId){
        Optional<RoshamboSession> optSession = getSession(sessionId);
        if(optSession.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        playerRepository = new PlayerRepository(optSession.get().getPlayers());
        Optional<Player> foundPlayer = playerRepository.findById(playerId);

        if(foundPlayer.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        removeMoveByPlayer(optSession.get(), foundPlayer.get());

        return ResponseEntity.noContent().build();
    }

    private void removeMoveByPlayer(RoshamboSession session, Player player){
        moveRepository = new MoveRepository(session.getMoves());
        moveRepository.removeByPlayerId(player.getId());
        session.getMoves().addAll(moveRepository.listAll());
    }

    private void addLinkToMoves(Integer sessionId, Player player) {
        Link link = linkTo(methodOn(PlayerController.class)
                .listAllPlayerMOve(sessionId, player.getId()))
                .withRel("moves");

        if(!player.hasLink("moves")){
            player.add(link);
        }
    }
}
