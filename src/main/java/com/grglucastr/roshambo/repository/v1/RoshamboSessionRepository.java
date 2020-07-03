package com.grglucastr.roshambo.repository.v1;

import com.grglucastr.roshambo.model.RoshamboSession;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoshamboSessionRepository extends BaseRepository<RoshamboSession>{

    private Set<RoshamboSession> gameSessions = new HashSet<>();

    @Override
    public RoshamboSession add(RoshamboSession obj) {
        RoshamboSession newGame = new RoshamboSession(getAvailableId(), obj.getName());
        gameSessions.add(newGame);
        return newGame;
    }

    @Override
    public List<RoshamboSession> listAll() {
        return gameSessions.stream().collect(Collectors.toList());
    }

    @Override
    public Optional<RoshamboSession> find(RoshamboSession obj) {
        return gameSessions.stream().filter(session -> session.equals(obj)).findFirst();
    }

    @Override
    public Optional<RoshamboSession> findById(Integer id) {
        return gameSessions.stream().filter(session -> session.getId().equals(id)).findFirst();
    }

    @Override
    public boolean remove(RoshamboSession obj) {
        List<RoshamboSession> lst = listAll();
        boolean remove = lst.remove(obj);

        if(remove){
            gameSessions.clear();
            gameSessions.addAll(lst);
        }

        return remove;
    }

    @Override
    public boolean removeById(Integer id) {
        List<RoshamboSession> lst = listAll();
        boolean remove = lst.removeIf(obj -> obj.getId() == id);

        if(remove){
            gameSessions.clear();
            gameSessions.addAll(lst);
        }

        return remove;
    }

    @Override
    public RoshamboSession update(RoshamboSession obj) {
        if(removeById(obj.getId())){
            gameSessions.add(obj);
        }
        return obj;
    }
}
