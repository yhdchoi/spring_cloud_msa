package com.yhdc.order_server.transaction;

import com.yhdc.order_server.object.OrderCreateRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> processOrder(@RequestBody OrderCreateRecord order) {
        return orderService.processOrder(order);
    }

}
