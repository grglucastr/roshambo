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

        Strategy s1 = new Strategy(1, "Rock");
        Strategy s2 = new Strategy(2, "Paper");
        Strategy s3 = new Strategy(3, "Scissor");
        Strategy s4 = new Strategy(4, "Spock");
        Strategy s5 = new Strategy(5, "Lizard");

        s1.setStrengths(s3, s5);
        s1.setWeaknesses(s2,s4);

        s2.setStrengths(s1, s4);
        s2.setWeaknesses(s3, s5);

        s3.setStrengths(s2, s5);
        s3.setWeaknesses(s1, s4);

        s4.setStrengths(s1, s3);
        s4.setWeaknesses(s2, s5);

        s5.setStrengths(s2, s4);
        s5.setWeaknesses(s1, s3);

        return Arrays.asList(s1, s2, s3, s4, s5);
    }
}