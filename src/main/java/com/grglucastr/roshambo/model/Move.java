package com.grglucastr.roshambo.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    // Helpers methods
    public static Set<Move> getSampleMoves(){
        Set<Move> moves = new HashSet<>();
        moves.add(new Move(1, Player.getPlayers().get(0), Strategy.getStrategies().get(0)));
        moves.add(new Move(2, Player.getPlayers().get(1), Strategy.getStrategies().get(1)));
        moves.add(new Move(3, Player.getPlayers().get(2), Strategy.getStrategies().get(2)));
        moves.add(new Move(4, Player.getPlayers().get(3), Strategy.getStrategies().get(3)));
        return moves;
    }

    public static Set<Move> getSampleMovesSameStrategy(){
        Set<Move> moves = new HashSet<>();
        moves.add(new Move(1, Player.getPlayers().get(0), Strategy.getStrategies().get(0)));
        moves.add(new Move(2, Player.getPlayers().get(1), Strategy.getStrategies().get(0)));
        moves.add(new Move(3, Player.getPlayers().get(2), Strategy.getStrategies().get(0)));
        moves.add(new Move(4, Player.getPlayers().get(3), Strategy.getStrategies().get(0)));
        return moves;
    }

    public static Set<Move> getSampleMovesWithWinnerStrategy(){
        Set<Move> moves = new HashSet<>();
        moves.add(new Move(1, Player.getPlayers().get(0), Strategy.getStrategies().get(1)));
        moves.add(new Move(2, Player.getPlayers().get(1), Strategy.getStrategies().get(1)));
        moves.add(new Move(3, Player.getPlayers().get(2), Strategy.getStrategies().get(2)));
        moves.add(new Move(4, Player.getPlayers().get(3), Strategy.getStrategies().get(4)));
        return moves;
    }
}
