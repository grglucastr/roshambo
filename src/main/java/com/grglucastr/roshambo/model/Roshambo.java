package com.grglucastr.roshambo.model;


import java.io.Serializable;
import java.util.*;

public class Roshambo extends Game {

    private static final int MINIMUM_PLAYERS_NUMBER = 2;
    private Set<Move> moves;
    private Set<Player> outs;
    private HashMap<Player, Player> beats;

    public Roshambo(Integer sessionId, Set<Player> players, Set<Move> moves) {
        super(sessionId, players);
        this.moves = moves;
    }

    @Override
    public void preStart() {
        if(!allPlayersHaveMove()){
            throw new RuntimeException("Unable to start. Not all players have made a move yet.");
        }
    }

    @Override
    public void runGameRule() {
        if(playersHaveSameStrategy()){
            setGameResult(GameResult.DRAW);
            return;
        }

        moves.forEach(move -> {
            moves.forEach(move2 -> {

            });
        });

    }

    @Override
    public void finish() {

    }

    @Override
    public boolean hasPlayers() {
        // This game is played at least with two players
        return getPlayers().size() >= MINIMUM_PLAYERS_NUMBER;
    }

    /*
    * Warranty that all the players have made a move
    * */
    public boolean allPlayersHaveMove(){
        Set<Player> lst = new HashSet<>();
        moves.forEach(move -> {
            lst.add(move.getPlayer());
        });
        return lst.size() == MINIMUM_PLAYERS_NUMBER;
    }

    public boolean playersHaveSameStrategy() {
        Set<Strategy> differentStrategies = new HashSet<>();
        moves.forEach(move -> {
            differentStrategies.add(move.getStrategy());
        });
        return differentStrategies.size() == 1;
    }
}
