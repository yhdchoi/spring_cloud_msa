package com.yhdc.order_server.transaction.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderProcessEvent {

    private String orderId;
    private String email;

}
