package com.grglucastr.roshambo.model;

import java.util.HashSet;
import java.util.Set;

public abstract class Game extends BaseObject {

    private GameStatus gameStatus;

    protected Set<Player> players;
    protected Player winner;
    protected GameResult gameResult;

    public Game(Integer id) {
        super(id);
        this.gameResult = GameResult.NOT_DEFINED;
        this.players = new HashSet<>();
    }

    public abstract void preStart();
    public abstract void runGameRule();
    public abstract void finish();
    public abstract Set<Player> loadPlayers();
    public abstract boolean hasPlayers();

    public void start(){
        if(!hasPlayers()){
            throw new RuntimeException("Unable to start. Game has not enough players.");
        }

        preStart();
        gameStatus = GameStatus.IN_PROGRESS;

        runGameRule();

        finish();
        gameStatus = GameStatus.FINISHED;
    }

    // Getters and Setters
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

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }
}
