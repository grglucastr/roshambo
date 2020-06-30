package com.grglucastr.roshambo.model;

import java.io.Serializable;
import java.util.Objects;

public class Move implements Serializable {

    private Integer id;
    private Player player;
    private Strategy strategy;

    public Move() {
    }

    public Move(Player player, Strategy strategy) {
        this.player = player;
        this.strategy = strategy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return id.equals(move.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
