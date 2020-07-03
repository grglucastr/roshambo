package com.grglucastr.roshambo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class RoshamboTest {

    @Mock
    private Roshambo roshambo;

    @BeforeEach
    void setUp() {
        roshambo = Mockito.spy(new Roshambo(111, getMoves()));
    }

    @Test
    public void gameIsLoadingPreSettingsConfigurations(){
        roshambo.start();
        Mockito.verify(roshambo, Mockito.times(1)).preStart();
    }

    @Test
    public void gameLoadingThrowsAnExceptionDueAllPlayersHaveNotMadeTheirMoveYet(){
        RuntimeException ex = assertThrows(RuntimeException.class, () ->{
            Move move = getMoves().stream().collect(Collectors.toList()).get(0);
            roshambo.getMoves().clear();
            roshambo.getMoves().add(move);
            roshambo.getMoves().add(move);
            roshambo.preStart();
        });

        String message = "Unable to start. Not all players have made a move yet.";

        assertNotNull(ex.getMessage());
        assertEquals(message, ex.getMessage());
    }

    @Test
    public void gameDoNotStartDueTheLackOfPlayers(){
        RuntimeException ex = assertThrows(RuntimeException.class, ()->{
            Player p = getPlayers().get(0);

            roshambo.getPlayers().clear();
            roshambo.getPlayers().add(p);
            roshambo.start();
        });

        String message = "Unable to start. Game has not enough players.";

        assertNotNull(ex.getMessage());
        assertEquals(message, ex.getMessage());
    }

    @Test
    public void runGameRuleWhenGameStart(){
        roshambo.start();
        Mockito.verify(roshambo, Mockito.times(1)).runGameRule();
    }

    @Test
    public void gameHasADrawDuePlayersBeatenOneAnother(){
        roshambo.start();
        assertEquals(GameStatus.FINISHED, roshambo.getGameStatus());
        assertEquals(GameResult.DRAW, roshambo.getGameResult());
        assertEquals(roshambo.getOuts().size(), roshambo.getPlayers().size());
        assertNull(roshambo.getWinner());
    }

    @Test
    public void gameHasDrawDueSameStrategy(){
        roshambo.getMoves().clear();
        roshambo.getMoves().addAll(getMovesSameStrategy());
        roshambo.start();

        assertEquals(GameStatus.FINISHED, roshambo.getGameStatus());
        assertEquals(GameResult.DRAW, roshambo.getGameResult());
        assertEquals(roshambo.getOuts().size(), roshambo.getPlayers().size());
        assertNull(roshambo.getWinner());
    }

    @Test
    public void gameHasWinner(){
        int qtyLosers = roshambo.getPlayers().size() - 1;
        int qtyPlayersThatBeats = 2;

        roshambo.getMoves().clear();
        roshambo.getMoves().addAll(getMovesWithWinnerStrategy());
        roshambo.start();

        assertEquals(GameStatus.FINISHED, roshambo.getGameStatus());
        assertEquals(GameResult.WINNER, roshambo.getGameResult());
        assertEquals(qtyLosers, roshambo.getOuts().size());
        assertEquals(qtyPlayersThatBeats, roshambo.getBeats().size());
        assertNotNull(roshambo.getWinner());
    }

    @Test
    public void allPlayersHaveMoveTest(){
        assertTrue(roshambo.allPlayersHaveMove());
    }

    @Test
    public void allPlayersHaveNotMoveTest(){

        Move movePlayer1 = getMoves().stream().collect(Collectors.toList()).get(0);

        roshambo.getMoves().clear();
        roshambo.getMoves().add(movePlayer1);
        roshambo.getMoves().add(movePlayer1);
        roshambo.getMoves().add(movePlayer1);
        roshambo.getMoves().add(movePlayer1);

        assertFalse(roshambo.allPlayersHaveMove());
    }

    @Test
    public void hasEnoughPlayersTest(){
        assertTrue(roshambo.hasPlayers());
    }

    @Test
    public void hasNotEnoughPlayers(){
        Player player = getPlayers().get(0);

        // Adding two times the same player does not count as if were two players
        roshambo.getPlayers().clear();
        roshambo.getPlayers().add(player);
        roshambo.getPlayers().add(player);

        assertFalse(roshambo.hasPlayers());
    }

    @Test
    public void playersHaveSameStrategyTest(){
        roshambo.getMoves().clear();
        roshambo.getMoves().addAll(getMovesSameStrategy());
        assertTrue(roshambo.playersHaveSameStrategy());
    }

    @Test
    public void playersHaveDifferentStrategiesTest(){
        assertFalse(roshambo.playersHaveSameStrategy());
    }

    // Helpers methods
    public Set<Move> getMoves(){
        Set<Move> moves = new HashSet<>();
        moves.add(new Move(1, getPlayers().get(0), getStrategies().get(0)));
        moves.add(new Move(2, getPlayers().get(1), getStrategies().get(1)));
        moves.add(new Move(3, getPlayers().get(2), getStrategies().get(2)));
        moves.add(new Move(4, getPlayers().get(3), getStrategies().get(3)));
        return moves;
    }

    public Set<Move> getMovesSameStrategy(){
        Set<Move> moves = new HashSet<>();
        moves.add(new Move(1, getPlayers().get(0), getStrategies().get(0)));
        moves.add(new Move(2, getPlayers().get(1), getStrategies().get(0)));
        moves.add(new Move(3, getPlayers().get(2), getStrategies().get(0)));
        moves.add(new Move(4, getPlayers().get(3), getStrategies().get(0)));
        return moves;
    }

    public Set<Move> getMovesWithWinnerStrategy(){
        Set<Move> moves = new HashSet<>();
        moves.add(new Move(1, getPlayers().get(0), getStrategies().get(1)));
        moves.add(new Move(2, getPlayers().get(1), getStrategies().get(1)));
        moves.add(new Move(3, getPlayers().get(2), getStrategies().get(2)));
        moves.add(new Move(4, getPlayers().get(3), getStrategies().get(4)));
        return moves;
    }

    public List<Player> getPlayers(){
        List<Player> players = Arrays.asList(
                new Player(1, "Player1"),
                new Player(2, "Player2"),
                new Player(3, "Player3"),
                new Player(4, "Player4")
        );
        return players;
    }

    public List<Strategy> getStrategies(){
        Strategy rock = new Strategy(1, "Rock");
        Strategy paper = new Strategy(2, "Paper");
        Strategy scissor = new Strategy(3, "Scissor");
        Strategy spock = new Strategy(4, "Spock");
        Strategy lizard = new Strategy(5, "Lizard");

        rock.setMultipleStrengths(scissor, lizard);
        rock.setMultipleWeaknesses(paper,spock, rock);

        paper.setMultipleStrengths(rock, spock);
        paper.setMultipleWeaknesses(scissor, lizard);

        scissor.setMultipleStrengths(paper, lizard);
        scissor.setMultipleWeaknesses(rock, spock);

        spock.setMultipleStrengths(rock, scissor);
        spock.setMultipleWeaknesses(paper, lizard);

        lizard.setMultipleStrengths(paper, spock);
        lizard.setMultipleWeaknesses(rock, scissor);

        return Arrays.asList(rock, paper, scissor, spock, lizard);
    }


}