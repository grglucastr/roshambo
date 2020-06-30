package com.grglucastr.roshambo.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class GameSession implements Serializable {

    private Integer id;
    private Set<Move> moves;
    private Set<Player> playersInProgress;
    private Set<Player> playersOnHold;

    public GameSession() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Move> getMoves() {
        return moves;
    }

    public void setMoves(Set<Move> moves) {
        this.moves = moves;
    }

    public Set<Player> getPlayersInProgress() {
        return playersInProgress;
    }

    public void setPlayersInProgress(Set<Player> playersInProgress) {
        this.playersInProgress = playersInProgress;
    }

    public Set<Player> getPlayersOnHold() {
        return playersOnHold;
    }

    public void setPlayersOnHold(Set<Player> playersOnHold) {
        this.playersOnHold = playersOnHold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSession that = (GameSession) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
