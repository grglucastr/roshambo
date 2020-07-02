package com.grglucastr.roshambo.repository.v1;

import com.grglucastr.roshambo.model.Player;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PlayerRepository extends BaseRepository<Player>{

    private Set<Player> players = new HashSet<>();

    @Override
    public Player add(Player obj) {
        Player newPlayer = new Player(getAvailableId(), obj.getName());
        players.add(newPlayer);
        return newPlayer;
    }

    @Override
    public List<Player> listAll() {
        return  players.stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Player> find(Player obj) {
        return players.stream().filter(player -> player.equals(obj)).findFirst();
    }

    public Optional<Player> findByUsername(String username) {
        return players.stream().filter(player -> player.getName().equalsIgnoreCase(username)).findFirst();
    }

    @Override
    public Optional<Player> findById(Integer id) {
        return players.stream().filter(player -> player.getId().equals(id)).findFirst();
    }

    @Override
    public boolean remove(Player obj) {
        List<Player> lst = listAll();
        boolean remove = lst.remove(obj);

        if(remove){
            players.clear();
            players.addAll(lst);
        }

        return  remove;
    }

    @Override
    public boolean removeById(Integer id) {
        List<Player> lst = listAll();
        boolean remove = lst.removeIf(player -> player.getId() == id);

        if(remove){
            players.clear();
            players.addAll(lst);
        }

        return  remove;
    }

    @Override
    public Player update(Player obj) {
        remove(obj);
        players.add(obj);
        return obj;
    }

}
