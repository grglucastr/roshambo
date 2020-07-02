package com.grglucastr.roshambo.model;

import java.util.*;

public class Strategy extends BaseObject {

    private String name;
    private Set<Strategy> weaknesses;
    private Set<Strategy> strengths;

    public Strategy(Integer id, String name) {
        super(id);
        this.name = name;
        this.weaknesses = new HashSet<>();
        this.strengths = new HashSet<>();
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

    public Set<Strategy> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(Set<Strategy> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public void setWeaknesses(Strategy... weaknesses) {
        for (int i = 0; i < weaknesses.length; i++) {
            this.weaknesses.add(weaknesses[i]);
        }
    }

    public Set<Strategy> getStrengths() {
        return strengths;
    }

    public void setStrengths(Set<Strategy> strengths) {
        this.strengths = strengths;
    }

    public void setStrengths(Strategy... strengths) {
        for (int i = 0; i < strengths.length; i++) {
            this.strengths.add(strengths[i]);
        }
    }
}
