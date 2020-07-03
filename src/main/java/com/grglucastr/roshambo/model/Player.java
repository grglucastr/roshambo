package com.grglucastr.roshambo.model;

import java.util.Arrays;
import java.util.List;

public class Player extends BaseObject {

    private String name;

    public Player(Integer id){
        super(id);
    }

    public Player(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Player> getPlayers(){
        List<Player> players = Arrays.asList(
                new Player(1, "Anna"),
                new Player(2, "Sven"),
                new Player(3, "Josh"),
                new Player(4, "Bob")
        );
        return players;
    }

}
