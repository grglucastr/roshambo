package com.grglucastr.roshambo.controller.v1;

import com.grglucastr.roshambo.model.Player;
import com.grglucastr.roshambo.repository.v1.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PlayerController {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping(value = {"/players"},  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>>listAllPlayers(){
        return ResponseEntity.ok(playerRepository.listAll());
    }

    @GetMapping(value = {"/players/{username}"},  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getPlayer(@PathVariable("username") String username){
        Optional<Player> player = playerRepository.findByUsername(username);

        return player.map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = {"/players"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> addNewPlayer(@RequestBody Player newPlayer){
        Player player = playerRepository.add(newPlayer);
        return ResponseEntity.status(HttpStatus.CREATED).body(player);
    }

    @PutMapping(value = {"/players/{username}"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> update(@PathVariable("username")String username, @RequestBody Player player){
        Optional<Player> foundPlayer = playerRepository.findByUsername(username);

        if(foundPlayer.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Player updatePlayer = foundPlayer.get();
        updatePlayer.setName(player.getName());
        playerRepository.update(updatePlayer);

        return ResponseEntity.ok(updatePlayer);
    }

    @DeleteMapping( value = {"/players/{id}"})
    public ResponseEntity deletePlayer(@PathVariable("id") Integer id){
        if(playerRepository.removeById(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
