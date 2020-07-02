package com.grglucastr.roshambo.repository.v1;

import com.grglucastr.roshambo.model.Strategy;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StrategyRepository extends BaseRepository<Strategy> {

    private Set<Strategy> strategies = new HashSet<>();

    @Override
    public Strategy add(Strategy obj) {
        Strategy newStrategy = new Strategy(getAvailableId(), obj.getName());

        if(obj.getWeaknesses().size() > 0){
            newStrategy.getWeaknesses().addAll(obj.getWeaknesses());
        }

        if(obj.getStrengths().size() > 0){
            newStrategy.getStrengths().addAll(obj.getStrengths());
        }

        strategies.add(newStrategy);
        return newStrategy;
    }

    @Override
    public List<Strategy> listAll() {
        return strategies.stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Strategy> find(Strategy obj) {
        return strategies.stream().filter(strategy -> strategy.equals(obj)).findFirst();
    }

    @Override
    public Optional<Strategy> findById(Integer id) {
        return strategies.stream().filter(strategy -> strategy.getId().equals(id)).findFirst();
    }

    @Override
    public boolean remove(Strategy obj) {
        List<Strategy> lst = listAll();
        boolean remove = lst.remove(obj);

        if(remove){
            strategies.clear();
            strategies.addAll(lst);
        }

        return  remove;
    }

    @Override
    public boolean removeById(Integer id) {
        List<Strategy> lst = listAll();
        boolean remove = lst.removeIf(strategy -> strategy.getId() == id);

        if(remove){
            strategies.clear();
            strategies.addAll(lst);
        }

        return  remove;
    }

    @Override
    public Strategy update(Strategy obj) {
        remove(obj);
        strategies.add(obj);
        return obj;
    }
}
