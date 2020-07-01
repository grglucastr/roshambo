package com.grglucastr.roshambo.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public abstract class Game implements Serializable {

    private GameStatus gameStatus;

    protected Integer sessionId;
    protected Set<Player> players;
    protected Player winner;
    protected GameResult gameResult;

    public Game(Integer sessionId, Set<Player> players) {
        this.sessionId = sessionId;
        this.players = players;
        this.gameResult = GameResult.NOT_DEFINED;
    }

    public abstract void preStart();
    public abstract void runGameRule();
    public abstract void finish();
    public abstract boolean hasPlayers();

    public void start(){

        preStart();

        if(!hasPlayers()){
            throw new RuntimeException("Unable to start. Game has not enough players.");
        }

        gameStatus = GameStatus.IN_PROGRESS;

        runGameRule();

        finish();
        gameStatus = GameStatus.FINISHED;
    }

    // Getters and Setters
    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    // Equals and Hashcodes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return sessionId.equals(game.sessionId) &&
                players.equals(game.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, players);
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }
}
