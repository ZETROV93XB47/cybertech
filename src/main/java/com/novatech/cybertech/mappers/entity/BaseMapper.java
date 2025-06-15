package com.novatech.cybertech.mappers.entity;

import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;

public interface BaseMapper <E,R,U,D>{
    D mapFromEntityToDto(E entity);

    E mapFromDtoToEntity(D dto);

    Collection<D> mapFromEntityToDto(Collection<E> entities);

    Collection<E> mapFromDtoToEntity(Collection<D> dtos);

    E mapFromCreationRequestToEntity(R r);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E mapFromUpdateRequestToEntity(U u);

    Collection<E> mapFromCreationRequestToEntity(Collection<R> rs);
}
