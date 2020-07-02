package com.grglucastr.roshambo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grglucastr.roshambo.util.SetStrategy;

public class Strategy extends BaseObject {

    private String name;
    private SetStrategy weaknesses;
    private SetStrategy strengths;

    public Strategy(Integer id) {
        super(id);
    }

    public Strategy(Integer id, String name) {
        super(id);
        this.name = name;
        this.weaknesses = new SetStrategy(this);
        this.strengths = new SetStrategy(this);
    }

    public boolean beats(Strategy strategy){
        if(this.equals(strategy)){
            return false;
        }
        return strategy.getWeaknesses().contains(this);
    }


    // Getters and setterss
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public SetStrategy getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(SetStrategy weaknesses) {
        this.weaknesses = weaknesses;
    }

    public void setMultipleWeaknesses(Strategy... weaknesses) {
        for (int i = 0; i < weaknesses.length; i++) {
            this.weaknesses.add(weaknesses[i]);
        }
    }

    @JsonIgnore
    public SetStrategy getStrengths() {
        return strengths;
    }

    public void setStrengths(SetStrategy strengths) {
        this.strengths = strengths;
    }

    public void setMultipleStrengths(Strategy... strengths) {
        for (int i = 0; i < strengths.length; i++) {
            this.strengths.add(strengths[i]);
        }
    }
}
