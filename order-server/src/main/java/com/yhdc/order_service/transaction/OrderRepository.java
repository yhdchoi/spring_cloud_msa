package com.yhdc.order_service.transaction;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

    Optional<Order> findByIdAndBuyerId(long orderId, String userId);

}
