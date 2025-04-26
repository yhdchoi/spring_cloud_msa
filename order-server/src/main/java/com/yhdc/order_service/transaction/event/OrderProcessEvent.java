package com.yhdc.order_service.transaction.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderProcessEvent {
    private String orderId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
