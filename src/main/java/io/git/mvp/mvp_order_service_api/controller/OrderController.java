package io.git.mvp.mvp_order_service_api.controller;

import io.git.mvp.mvp_order_service_api.model.Order;
import io.git.mvp.mvp_order_service_api.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService orderService) {
        this.service = orderService;
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    @GetMapping
    public List<Order> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return service.findById(id);
    }
}
