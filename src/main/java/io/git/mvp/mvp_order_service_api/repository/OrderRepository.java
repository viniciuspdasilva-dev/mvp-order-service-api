package io.git.mvp.mvp_order_service_api.repository;

import io.git.mvp.mvp_order_service_api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
