package com.novatech.cybertech.api.controllers;

import com.novatech.cybertech.api.controllers.spec.OrderManagementControllerApiSpec;
import com.novatech.cybertech.dto.data.CustomUserDetails;
import com.novatech.cybertech.dto.request.order.OrderPlacingRequestDto;
import com.novatech.cybertech.dto.response.order.OrderResponseDto;
import com.novatech.cybertech.services.core.OrderService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.novatech.cybertech.constants.CyberTechAppConstants.ORDER_MANAGEMENT_CONTROLLER_BASE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(ORDER_MANAGEMENT_CONTROLLER_BASE_PATH)
@Tag(name = " OrderManagementController", description = "API for managing Orders")
public class OrderManagementController implements OrderManagementControllerApiSpec {

    private final OrderService orderService;

    @Override
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @PostMapping(value = "/place", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDto> placeOrder(@Valid @RequestBody final OrderPlacingRequestDto orderPlacingRequestDto, @AuthenticationPrincipal final CustomUserDetails currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(orderPlacingRequestDto, currentUser.getUser()));
    }
}
