package com.grglucastr.roshambo.model;
import java.util.*;

public class Roshambo extends Game {

    public static final int MINIMUM_PLAYERS_NUMBER = 2;

    private Set<Move> moves;
    private Set<Player> outs;
    private HashMap<Player, List<Player>> beats;

    public Roshambo(Integer sessionId, Set<Player> players, Set<Move> moves) {
        super(sessionId, players);
        this.moves = moves;
        outs = new HashSet<>();
        beats = new HashMap<>();
    }

    @Override
    public void preStart() {
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
                    List<Player> playersBeaten = new ArrayList<>();

                    if(beats.containsKey(move.getPlayer())){
                        playersBeaten = beats.get(move.getPlayer());
                    }

                    playersBeaten.add(move2.getPlayer());
                    beats.put(move.getPlayer(), playersBeaten);

                    outs.add(move2.getPlayer());
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

    /*
    * Warranty that all the players have made a move
    * */
    public boolean allPlayersHaveMove(){
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

    public Set<Player> getOuts() {
        return outs;
    }

    public HashMap<Player, List<Player>> getBeats() {
        return beats;
    }

    public Set<Move> getMoves() {
        return moves;
    }
}
