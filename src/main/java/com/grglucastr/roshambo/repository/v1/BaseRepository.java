package com.grglucastr.roshambo.repository.v1;

import com.grglucastr.roshambo.model.BaseObject;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Repository
public abstract class BaseRepository<T extends BaseObject> implements DataManageable<Integer, T> {

    protected Integer getAvailableId(){
        List<T> lst = listAll();

        if(lst.size() == 0){
            return 1;
        }

        Comparator<T> comparatorById = Comparator.comparing(T::getId);
        Collections.sort(lst, comparatorById.reversed());
        return lst.get(0).getId() + 1;
    }
}
