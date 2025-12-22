package com.novatech.cybertech.api.controllers.spec;

import com.novatech.cybertech.api.error.model.ErrorResponseDto;
import com.novatech.cybertech.dto.data.CustomUserDetails;
import com.novatech.cybertech.dto.request.order.OrderCreateRequestDto;
import com.novatech.cybertech.dto.request.order.OrderUpdateRequestDto;
import com.novatech.cybertech.dto.response.order.OrderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public interface OrderCrudControllerApiSpec {

    @Operation(summary = "Request a Order by UUID",
            description = "Fetches a Order's details based on their unique UUID.",
            parameters = {
                    @Parameter(name = "uuid", description = "UUID for searching a Order", required = true, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order found successfully", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Operation forbidden", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class)))})
    ResponseEntity<OrderResponseDto> getOrderByUuid(final UUID orderUuid);


    @Operation(summary = "Create a new Order",
            description = "Registers a new Order in the system.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Order data for creation. All fields are mandatory",
                    required = true,
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderCreateRequestDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created successfully",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict - Order already exists",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error during Order creation",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    ResponseEntity<OrderResponseDto> createOrder(final OrderCreateRequestDto orderCreateRequestDto);


    @Operation(summary = "Update an existing Order by UUID",
            description = "Updates an existing order's details based on their unique UUID. Fields not provided will not be updated.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Order data for update. Only provide fields that need to be changed.", required = true, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderUpdateRequestDto.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order updated successfully",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data / Validation error",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Operation forbidden",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error during order update",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    ResponseEntity<OrderResponseDto> updateOrder(final OrderUpdateRequestDto orderUpdateRequestDto);


    @Operation(summary = "Delete a Order by UUID",
            description = "Deletes a order based on their unique UUID.",
            parameters = {
                    @Parameter(name = "uuid", description = "The UUID of the order to delete", required = true, schema = @Schema(implementation = UUID.class))
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Order deleted successfully (No Content)"),
                    @ApiResponse(responseCode = "400", description = "Bad request (e.g., invalid UUID format)",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Operation forbidden",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error during order deletion",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    ResponseEntity<Void> deleteOrderByUuid(final UUID orderUuid);
}
