package com.grglucastr.roshambo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grglucastr.roshambo.util.SetSubStrategy;

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
}
