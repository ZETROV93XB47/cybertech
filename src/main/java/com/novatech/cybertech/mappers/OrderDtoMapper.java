package com.novatech.cybertech.mappers;

import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.models.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderDtoMapper {
     Order mapFromEntityToModel(OrderEntity orderEntity);
     OrderEntity mapFromModelToEntity(Order order);
}
