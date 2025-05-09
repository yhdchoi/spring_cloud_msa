package com.yhdc.order_service.transaction;

import com.yhdc.order_service.transaction.object.OrderRequestRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderServiceImpl orderService;

    /**
     * PROCESS NEW ORDER
     *
     * @param order
     * @apiNote Processing order by checking inventory
     */
    @PostMapping("/process")
    public ResponseEntity<?> processOrder(@RequestBody OrderRequestRecord order) {
        return orderService.processOrder(order);
    }


    @PatchMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestParam String orderId,
                                         @RequestParam String userId) {
        return orderService.cancelOrder(orderId, userId);
    }

}
