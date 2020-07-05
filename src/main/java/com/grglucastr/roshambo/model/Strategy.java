package com.grglucastr.roshambo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grglucastr.roshambo.util.SetSubStrategy;

import java.util.Arrays;
import java.util.List;

public class Strategy extends BaseObject {

    private String name;
    private SetSubStrategy weaknesses;
    private SetSubStrategy strengths;

    public Strategy(Integer id) {
        super(id);
    }

    public Strategy(Integer id, String name) {
        super(id);
        this.name = name;
        this.weaknesses = new SetSubStrategy(this);
        this.strengths = new SetSubStrategy(this);
    }

    public boolean beats(Strategy strategy){
        if(this.equals(strategy)){
            return false;
        }
        return strategy.getWeaknesses().contains(this);
    }


    // Getters and setterss
    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public SetSubStrategy getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(SetSubStrategy weaknesses) {
        this.weaknesses = weaknesses;
    }

    public void setMultipleWeaknesses(Strategy... weaknesses) {
        for (int i = 0; i < weaknesses.length; i++) {
            this.weaknesses.add(weaknesses[i]);
        }
    }

    @JsonIgnore
    public SetSubStrategy getStrengths() {
        return strengths;
    }

    public void setStrengths(SetSubStrategy strengths) {
        this.strengths = strengths;
    }

    public void setMultipleStrengths(Strategy... strengths) {
        for (int i = 0; i < strengths.length; i++) {
            this.strengths.add(strengths[i]);
        }
    }

    public static List<Strategy> getSampleStrategies(){
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
