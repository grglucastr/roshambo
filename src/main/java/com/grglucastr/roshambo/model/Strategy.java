package com.grglucastr.roshambo.model;

import java.io.Serializable;
import java.util.*;

public class Strategy implements Serializable {

    private Integer id;
    private String name;
    private Set<Strategy> weaknesses;
    private Set<Strategy> strengths;

    public Strategy() {
        this.weaknesses = new HashSet<>();
        this.strengths = new HashSet<>();
    }

    public Strategy(Integer id, String name) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Strategy strategy = (Strategy) o;
        return id.equals(strategy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
