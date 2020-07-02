package com.grglucastr.roshambo.model;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {

    @Test
    public void rockBeatsScissorTest(){
        Strategy rock = getStrategies().get(0);
        Strategy scissor = getStrategies().get(2);
        assertTrue(rock.beats(scissor));
    }

    @Test
    public void paperBeatsRockTest(){
        Strategy paper = getStrategies().get(1);
        Strategy rock = getStrategies().get(0);
        assertTrue(paper.beats(rock));
    }

    @Test
    public void scissorBeatsPaperTest(){
        Strategy scissor = getStrategies().get(2);
        Strategy paper = getStrategies().get(1);
        assertTrue(scissor.beats(paper));
    }

    @Test
    public void spockShallNotBeatsLizardTest(){
        Strategy spock = getStrategies().get(3);
        Strategy lizard = getStrategies().get(4);
        assertFalse(spock.beats(lizard));
    }

    @Test
    public void rockShallNotBeatsRock(){
        Strategy rock1 = getStrategies().get(0);
        Strategy rock2 = getStrategies().get(0);
        assertFalse(rock1.beats(rock2));
    }

    @Test
    public void spockShallNotBeatsSpock(){
        Strategy spock = getStrategies().get(3);
        assertFalse(spock.beats(spock));
    }


    public List<Strategy> getStrategies(){

        Strategy rock = new Strategy(1, "Rock");
        Strategy paper = new Strategy(2, "Paper");
        Strategy scissor = new Strategy(3, "Scissor");
        Strategy spock = new Strategy(4, "Spock");
        Strategy lizard = new Strategy(5, "Lizard");

        rock.setMultipleStrengths(scissor, lizard);
        rock.setMultipleWeaknesses(paper,spock,rock);

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