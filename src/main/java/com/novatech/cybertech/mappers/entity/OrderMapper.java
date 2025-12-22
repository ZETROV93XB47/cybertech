package com.novatech.cybertech.mappers.entity;

import com.novatech.cybertech.dto.request.order.OrderCreateRequestDto;
import com.novatech.cybertech.dto.request.order.OrderPlacingRequestDto;
import com.novatech.cybertech.dto.request.order.OrderUpdateRequestDto;
import com.novatech.cybertech.dto.response.order.OrderItemResponseDto;
import com.novatech.cybertech.dto.response.order.OrderResponseDto;
import com.novatech.cybertech.entities.OrderEntity;
import com.novatech.cybertech.entities.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrderEntity, OrderCreateRequestDto, OrderUpdateRequestDto, OrderResponseDto> {
    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    OrderEntity mapFromOrderPlacingRequestDtoToOrderEntity(final OrderPlacingRequestDto orderPlacingRequestDto);


    @Override
    @Mapping(target = "userUuid", expression = "java(orderEntity.getUserEntity().getUuid())")
    @Mapping(target = "orderItems", source = "orderItemEntities")
    OrderResponseDto mapFromEntityToResponseDto(final OrderEntity orderEntity);

    @Mapping(target = "orderItemUuid", source = "uuid")
    @Mapping(target = "productUuid", source = "productEntity.uuid")
    @Mapping(target = "productName", source = "productEntity.name")
    @Mapping(target = "lineItemTotalPrice", source = "subtotal")
    OrderItemResponseDto mapFromOrderItemEntityToOrderItemResponseDto(final OrderItemEntity orderItemEntity);

}