package io.git.mvp.mvp_order_service_api.service;

import io.git.mvp.mvp_order_service_api.client.FakeStoreClient;
import io.git.mvp.mvp_order_service_api.kafka.OrderProducer;
import io.git.mvp.mvp_order_service_api.model.Order;
import io.git.mvp.mvp_order_service_api.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FakeStoreClient fakeStoreClient;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository, FakeStoreClient fakeStoreClient, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.fakeStoreClient = fakeStoreClient;
        this.orderProducer = orderProducer;
    }

    public Order create(Order order){
        if (fakeStoreClient.productExists(order.getProductId())) {
            throw new IllegalArgumentException("Product not found in estoque");
        }
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        Order orderSaved = orderRepository.save(order);
        orderProducer.send(orderSaved.getId());
        return orderSaved;
    }

    public Order updateStatus(Long orderId, String status){
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Order findById(Long orderId){
        return orderRepository.findById(orderId).orElseThrow();
    }
}
