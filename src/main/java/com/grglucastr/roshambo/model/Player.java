package com.grglucastr.roshambo.model;

public class Player extends BaseObject {

    private String name;

    public Player(Integer id){
        super(id);
    }

    public Player(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
