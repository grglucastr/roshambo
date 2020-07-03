package com.grglucastr.roshambo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class RoshamboSessionTest {

    private RoshamboSession roshambo;

    @BeforeEach
    void setUp() {
        roshambo = Mockito.spy(new RoshamboSession(111, "Who chooses next party playlist"));
        roshambo.getPlayers().addAll(Player.getPlayers());
        roshambo.getMoves().addAll(Move.getSampleMoves());
    }

    @Test
    public void gameIsLoadingPreSettingsConfigurations(){
        roshambo.start();
        Mockito.verify(roshambo, Mockito.times(1)).preStart();
    }

    @Test
    public void gameLoadingThrowsAnExceptionDueAllPlayersHaveNotMadeTheirMoveYet(){
        RuntimeException ex = assertThrows(RuntimeException.class, () ->{
            Move move = Move.getSampleMoves().stream().collect(Collectors.toList()).get(0);
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
    public void gameThrowsAnExceptionDueTheLackOfPlayers(){
        RuntimeException ex = assertThrows(RuntimeException.class, ()->{
            Player p = Player.getPlayers().get(0);

            roshambo.getPlayers().clear();
            roshambo.getPlayers().add(p);
            roshambo.start();
        });

        String message = "Unable to start. Game has not enough players.";

        assertNotNull(ex.getMessage());
        assertEquals(message, ex.getMessage());
    }

    @Test
    public void gameThrowsAnExceptionDueItStrategiesListIsEmpty(){
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
           roshambo.getStrategies().clear();
           roshambo.start();
        });

        String message = "Unable to start. Game session does not have any strategy.";

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
        roshambo.getMoves().addAll(Move.getSampleMovesSameStrategy());
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
        roshambo.getMoves().addAll(Move.getSampleMovesWithWinnerStrategy());
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

        Move movePlayer1 = Move.getSampleMoves().stream().collect(Collectors.toList()).get(0);

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
        Player player = Player.getPlayers().get(0);

        // Adding two times the same player does not count as if were two players
        roshambo.getPlayers().clear();
        roshambo.getPlayers().add(player);
        roshambo.getPlayers().add(player);

        assertFalse(roshambo.hasPlayers());
    }

    @Test
    public void playersHaveSameStrategyTest(){
        roshambo.getMoves().clear();
        roshambo.getMoves().addAll(Move.getSampleMovesSameStrategy());
        assertTrue(roshambo.playersHaveSameStrategy());
    }

    @Test
    public void playersHaveDifferentStrategiesTest(){
        assertFalse(roshambo.playersHaveSameStrategy());
    }

    @Test
    public void gameHasItOwnsStrategies(){
        assertTrue(roshambo.getStrategies().size() > 0);
    }

    @Test
    public void gameDoesNotHaveAnyStrategy(){
        roshambo.getStrategies().clear();
        assertTrue(roshambo.getStrategies().isEmpty());
    }
}