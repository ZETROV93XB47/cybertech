package com.novatech.cybertech.mappers.dto;


import java.util.Collection;

public interface DtoBaseMapper<M,D,R> {
    M mapFromDtoToModel(D dto);
    D mapFromModelToDto(M model);
    M mapFromRequestDtoToModel(R r);
    Collection<M> mapFromDtoToModel(final Collection<D> dtos);
    Collection<D> mapFromModelToDto(final Collection<M> models);
}
