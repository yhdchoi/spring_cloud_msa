package com.yhdc.product_server.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.yhdc.product_server.type.Constants.COMMON_RESPONSE_STATUS_ERROR;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonResponseRecord {
    private String msg;
    private String status = COMMON_RESPONSE_STATUS_ERROR;
}
