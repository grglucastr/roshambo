package com.grglucastr.roshambo.controller.v1;

import org.springframework.http.ResponseEntity;
import java.util.List;


public interface HTTPRequestable<T> {

    ResponseEntity<List<T>> listAll();
    ResponseEntity<T> getSingleOne(Integer id);
    ResponseEntity<T> addNew(T newObj);
    ResponseEntity<T> update(Integer id, T obj);
    ResponseEntity delete(Integer id);
}
