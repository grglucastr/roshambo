package com.grglucastr.roshambo.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class Strategy implements Serializable {

    private Integer id;
    private String name;
    private Set<Strategy> weaknesses;
    private Set<Strategy> strengths;

    public Strategy() {
    }

    public Strategy(String name, Set<Strategy> weaknesses, Set<Strategy> strengths) {
        this.name = name;
        this.weaknesses = weaknesses;
        this.strengths = strengths;
    }

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

    public Set<Strategy> getStrengths() {
        return strengths;
    }

    public void setStrengths(Set<Strategy> strengths) {
        this.strengths = strengths;
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
