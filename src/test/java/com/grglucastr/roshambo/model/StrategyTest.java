package com.grglucastr.roshambo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {

    private List<Strategy> strategies;

    @BeforeEach
    public void setUp(){
        this.strategies = Strategy.getStrategies();
    }

    @Test
    public void rockBeatsScissorTest(){
        Strategy rock = strategies.get(0);
        Strategy scissor = strategies.get(2);
        assertTrue(rock.beats(scissor));
    }

    @Test
    public void paperBeatsRockTest(){
        Strategy paper = strategies.get(1);
        Strategy rock = strategies.get(0);
        assertTrue(paper.beats(rock));
    }

    @Test
    public void scissorBeatsPaperTest(){
        Strategy scissor = strategies.get(2);
        Strategy paper = strategies.get(1);
        assertTrue(scissor.beats(paper));
    }

    @Test
    public void spockShallNotBeatsLizardTest(){
        Strategy spock = strategies.get(3);
        Strategy lizard = strategies.get(4);
        assertFalse(spock.beats(lizard));
    }

    @Test
    public void rockShallNotBeatsRock(){
        Strategy rock1 = strategies.get(0);
        Strategy rock2 = strategies.get(0);
        assertFalse(rock1.beats(rock2));
    }

    @Test
    public void spockShallNotBeatsSpock(){
        Strategy spock = strategies.get(3);
        assertFalse(spock.beats(spock));
    }
}