package com.grglucastr.roshambo.model;

import java.util.Objects;

public class Move extends BaseObject {

    private Player player;
    private Strategy strategy;

    public Move(Integer id) {
        super(id);
    }

    public Move(Integer id, Player player, Strategy strategy) {
        super(id);
        this.player = player;
        this.strategy = strategy;
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
        return player.equals(move.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }
}
