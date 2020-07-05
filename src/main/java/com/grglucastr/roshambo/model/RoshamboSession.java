package com.grglucastr.roshambo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public class RoshamboSession extends Game {

    public static final int MINIMUM_PLAYERS_NUMBER = 2;

    private String name;

    @JsonIgnore
    private Set<Move> moves;
    @JsonIgnore
    private Set<Strategy> strategies;
    @JsonIgnore
    private Set<Player> outs;
    @JsonIgnore
    private HashMap<Player, List<Player>> beats;

    public RoshamboSession(Integer id, String name) {
        super(id);
        this.name = name;
        this.moves = new HashSet<>();
        this.strategies = new HashSet<>(Strategy.getSampleStrategies());
        this.outs = new HashSet<>();
        this.beats = new HashMap<>();
    }

    @Override
    public void preStart() {

        outs.clear();
        beats.clear();
        setWinner(null);

        if(strategies.isEmpty()){
            throw new RuntimeException("Unable to start. Game session does not have any strategy.");
        }

        if(!allPlayersHaveMove()){
            throw new RuntimeException("Unable to start. Not all players have made a move yet.");
        }
    }

    @Override
    public void runGameRule() {
        // Its a draw
        if(playersHaveSameStrategy()){
            outs.addAll(players);
            return;
        }

        moves.forEach(move -> {
            moves.forEach(move2 -> {
                if(move.getStrategy().beats(move2.getStrategy())){
                    updateBeatsReports(move, move2);
                    updateOutsList(move2);
                }
            });
        });
    }

    @Override
    public void finish() {
        if(outs.size() == getPlayers().size()){
            setGameResult(GameResult.DRAW);
            return;
        }

        for(Player player : getPlayers()){
            if(!outs.contains(player)){
                setWinner(player);
                break;
            }
        }

        if(getWinner() != null){
            setGameResult(GameResult.WINNER);
        }
    }

    @Override
    public boolean hasPlayers() {
        // This game is played at least with two players
        return getPlayers().size() >= MINIMUM_PLAYERS_NUMBER;
    }


    public boolean allPlayersHaveMove(){
        if(moves.size() == 0){
            return false;
        }

        Set<Player> lst = new HashSet<>();
        moves.forEach(move -> {
            lst.add(move.getPlayer());
        });
        return lst.size() == getPlayers().size();
    }

    public boolean playersHaveSameStrategy(){
        Set<Strategy> strategies = new HashSet<>();
        moves.forEach(move -> {
            strategies.add(move.getStrategy());
        });
        return strategies.size() == 1;
    }

    public void updateBeatsReports(Move move, Move moveBeaten){
        List<Player> playersBeaten = new ArrayList<>();

        if(beats.containsKey(move.getPlayer())){
            playersBeaten = beats.get(move.getPlayer());
        }

        playersBeaten.add(moveBeaten.getPlayer());
        beats.put(move.getPlayer(), playersBeaten);
    }

    public  void updateOutsList(Move moveBeaten){
        outs.add(moveBeaten.getPlayer());
    }

    // Getters and Setters
    public Set<Player> getOuts() {
        return outs;
    }

    public HashMap<Player, List<Player>> getBeats() {
        return beats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Move> getMoves() {
        return moves;
    }

    public void setMoves(Set<Move> moves) {
        this.moves = moves;
    }

    public Set<Strategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(Set<Strategy> strategies) {
        this.strategies = strategies;
    }


    @Override
    @JsonIgnore
    public Set<Player> getPlayers() {
        return super.getPlayers();
    }

    @Override
    @JsonIgnore
    public GameStatus getGameStatus() {
        return super.getGameStatus();
    }

    @Override
    @JsonIgnore
    public Player getWinner() {
        return super.getWinner();
    }

    @Override
    @JsonIgnore
    public GameResult getGameResult() {
        return super.getGameResult();
    }
}
