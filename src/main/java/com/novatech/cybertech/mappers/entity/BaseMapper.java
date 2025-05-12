package com.novatech.cybertech.mappers.entity;

import java.util.Collection;

public interface BaseMapper <E,R,D>{
    D mapFromEntityToDto(E entity);
    E mapFromDtoToEntity(D dto);
    Collection<D> mapFromEntityToDto(Collection<E> entities);
    Collection<E> mapFromDtoToEntity(Collection<D> dtos);
    E mapFromRequestToEntity(R r);
    Collection<E> mapFromRequestToEntity(Collection<R> rs);
}
