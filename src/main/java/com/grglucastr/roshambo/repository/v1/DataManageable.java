package com.grglucastr.roshambo.repository.v1;

import java.util.*;

public interface DataManageable<T, U> {

    U add(U obj);
    List<U> listAll();
    Optional<U> find(U obj);
    Optional<U> findById(T id);
    boolean remove(U obj);
    boolean removeById(T id);
    U update(U obj);

}
