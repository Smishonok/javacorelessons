package com.valentin_nikolaev.javacore.finalWork.repository;

import java.util.List;

public interface GenericRepository<T,ID> {

    void add(ID id);

    T get(ID id);

    void remove(ID id);

    List<T> getAll();

    void removeAll();

}
