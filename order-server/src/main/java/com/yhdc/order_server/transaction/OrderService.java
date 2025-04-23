package com.yhdc.order_server.transaction;

import com.yhdc.order_server.transaction.object.OrderRequestRecord;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<?> processOrder(OrderRequestRecord orderRequestRecord);

    ResponseEntity<?> cancelOrder(String orderId, String userId);

}
