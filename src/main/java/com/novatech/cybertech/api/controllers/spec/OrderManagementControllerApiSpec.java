package com.novatech.cybertech.api.controllers.spec;

import com.novatech.cybertech.api.error.model.ErrorResponseDto;
import com.novatech.cybertech.dto.data.CustomUserDetails;
import com.novatech.cybertech.dto.request.order.OrderPlacingRequestDto;
import com.novatech.cybertech.dto.response.order.OrderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface OrderManagementControllerApiSpec {

    @Operation(
            summary = "Place a new order (authenticated users only)",
            description = """
                    Creates a new order for the authenticated user.
                    The request must include the list of products to purchase, quantities,
                    shipping address, and payment type.
                    Requires a valid JWT token in the Authorization header (Bearer token).
                    """,
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @RequestBody(
                    description = "Order creation data for the currently authenticated user.",
                    required = true,
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderPlacingRequestDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order placed successfully", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request or validation error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403",description = "Forbidden - user not allowed to perform this operation",content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "500",description = "Internal server error while placing the order",content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class)))
            }
    )
    ResponseEntity<OrderResponseDto> placeOrder(final OrderPlacingRequestDto orderPlacingRequestDto, final CustomUserDetails currentUser);

}
