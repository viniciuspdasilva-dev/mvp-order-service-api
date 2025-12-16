package io.git.mvp.mvp_order_service_api.controller;

import io.git.mvp.mvp_order_service_api.exceptions.OrderNotFoundException;
import io.git.mvp.mvp_order_service_api.model.Order;
import io.git.mvp.mvp_order_service_api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Tag(name = "Order", description = "Endpoint for managing a purchase order.")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService orderService) {
        this.service = orderService;
    }

    @GetMapping
    @Operation(
            summary = "Lists all purchase orders placed, with all statuses."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Purchase orders",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                        schema = @Schema(implementation = Order.class)
                                    )
                            )
                    )
            }
    )
    public List<Order> getAll(@RequestParam(required = false) String status) {
        if (status != null) {
            return service.findAllByStatus(status);
        }
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a order by their ID",
            description = "Returns a specific order from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved purchase orders",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Purpose order not found by ID",
                    content = @Content(schema = @Schema(implementation = OrderNotFoundException.class))), // Empty content for 404
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = RuntimeException.class)))
    })
    public Order getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(
            summary = "Update a purchase order",
            description = "Create a new purchase order and save it to the database, in addition to creating a message in Kafka."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create a new purchase order",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = RuntimeException.class)))
    })
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return new ResponseEntity<>(service.create(order), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    @Operation(
            summary = "Update a purchase order",
            description = "Update the new purchase order and save it to the database, in addition to creating a message in Kafka."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create a new purchase order",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = RuntimeException.class)))
    })
    public Order updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a purchase order.",
            description = "Delete a payment order by changing its status to canceled and sending a message in Kafka."
    )
    public ResponseEntity<String> remove(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Ordem removida com sucesso!");
    }

}
