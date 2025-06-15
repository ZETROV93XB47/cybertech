package com.novatech.cybertech.api.controllers;

import com.novatech.cybertech.api.controllers.spec.OrderControllerApiSpec;
import com.novatech.cybertech.dto.request.order.OrderCreateRequestDto;
import com.novatech.cybertech.dto.request.order.OrderUpdateRequestDto;
import com.novatech.cybertech.dto.response.order.OrderResponseDto;
import com.novatech.cybertech.services.implementation.OrderServiceImp;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.novatech.cybertech.constants.CyberTechAppConstants.ORDER_CONTROLLER_BASE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(ORDER_CONTROLLER_BASE_PATH)
@Tag(name = " OrderController", description = "API for Cart management")
public class OrderController implements OrderControllerApiSpec {


    private final OrderServiceImp orderService;

    @Override
    @GetMapping(value = "/get/{orderUuid}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDto> getOrderByUuid(@PathVariable("orderUuid") UUID orderUuid) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getByUUID(orderUuid));
    }

    @Override
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderCreateRequestDto orderCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(orderCreateRequestDto));
    }

    @Override
    @PatchMapping(value = "/update/{orderUuid}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDto> updateOrder(final OrderUpdateRequestDto orderUpdateRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.update(orderUpdateRequestDto));
    }

    @Override
    @DeleteMapping(value = "/delete/{orderUuid}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteOrderByUuid(UUID orderUuid) {
        orderService.deleteByUUID(orderUuid);
        return ResponseEntity.noContent().build();
    }
}
