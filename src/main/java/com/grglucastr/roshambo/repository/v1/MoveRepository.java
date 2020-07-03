package com.grglucastr.roshambo.repository.v1;

import com.grglucastr.roshambo.model.Move;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class MoveRepository extends BaseRepository<Move> {

    private Set<Move> moves = new HashSet<>();

    @Override
    public Move add(Move obj) {
        Move newMove = new Move(getAvailableId(), obj.getPlayer(), obj.getStrategy());
        moves.add(newMove);
        return newMove;
    }

    @Override
    public List<Move> listAll() {
        return moves.stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Move> find(Move obj) {
        return moves.stream().filter(move -> move.equals(obj)).findFirst();
    }

    @Override
    public Optional<Move> findById(Integer id) {
        return moves.stream().filter(move -> move.getId().equals(id)).findFirst();
    }

    @Override
    public boolean remove(Move obj) {
        List<Move> lst = listAll();
        boolean remove = lst.remove(obj);

        if(remove){
            moves.clear();
            moves.addAll(lst);
        }

        return remove;
    }

    @Override
    public boolean removeById(Integer id) {
        List<Move> lst = listAll();
        boolean remove = lst.removeIf(obj -> obj.getId() == id);

        if(remove){
            moves.clear();
            moves.addAll(lst);
        }

        return remove;
    }

    @Override
    public Move update(Move obj) {
        if(removeById(obj.getId())){
            moves.add(obj);
        }
        return obj;
    }

    public Optional<Move> findByPlayerId(Integer playerId){
        return moves.stream().filter(m -> m.getPlayer().getId().equals(playerId)).findFirst();
    }

}
