package com.novatech.cybertech.mappers.entity;

import java.util.Collection;

public interface BaseMapper <E,R,D,U>{
    D mapFromEntityToDto(E entity);
    E mapFromDtoToEntity(D dto);
    Collection<D> mapFromEntityToDto(Collection<E> entities);
    Collection<E> mapFromDtoToEntity(Collection<D> dtos);
    E mapFromCreationRequestToEntity(R r);
    E mapFromUpdateRequestToEntity(U u);
    Collection<E> mapFromCreationRequestToEntity(Collection<R> rs);
}
