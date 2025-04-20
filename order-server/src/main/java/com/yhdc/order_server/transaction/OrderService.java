package com.yhdc.order_server.transaction;

import com.yhdc.order_server.transaction.object.OrderCreateRecord;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<?> processOrder(OrderCreateRecord orderCreateRecord);

    ResponseEntity<?> cancelOrder(String orderId, String userId);

}
