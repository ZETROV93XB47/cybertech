package com.novatech.cybertech.mappers.entity;

import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;

public interface BaseMapper <ENTITY, CREATE, UPDATE, RESPONSE> {

    RESPONSE mapFromEntityToResponseDto(ENTITY entity);
    Collection<RESPONSE> mapFromEntityToResponseDto(Collection<ENTITY> entities);

    //ENTITY mapFromCreationRequestDtoToEntity(CREATE dto);
    //Collection<ENTITY> mapFromCreationRequestDtoToEntity(Collection<CREATE> dtos);

    ENTITY mapFromCreationRequestToEntity(CREATE r);
    Collection<ENTITY> mapFromCreationRequestToEntity(Collection<CREATE> rs);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ENTITY mapFromUpdateRequestToEntity(UPDATE u);
}
