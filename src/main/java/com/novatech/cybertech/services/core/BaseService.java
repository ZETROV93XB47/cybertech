package com.novatech.cybertech.services.core;

import java.util.Collection;

public interface BaseService<T, U, V> {

    Collection<V> getAll();

    V getByUUID(T t);

    Collection<V> getByUUIDs(Collection<T> ts);

    V create(U u);

    V update(U u);

    void deleteByUUID(T t);
    void deleteByUUIDs(Collection<T> ts);

}