package com.grglucastr.roshambo.util;
import com.grglucastr.roshambo.model.Strategy;

import java.util.HashSet;

public class SetStrategy extends HashSet<Strategy> {

    private Strategy parent;

    public SetStrategy(Strategy parent){
        this.parent = parent;
    }

    public boolean add(Strategy strategy) {
        if(parent.equals(strategy)){
            return false;
        }
        return super.add(strategy);
    }
}
