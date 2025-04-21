package com.yhdc.file_server.transaction.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.yhdc.file_server.transaction.type.Constants.COMMON_RESPONSE_STATUS_ERROR;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonResponseDto {
    private String msg;
    private String status = COMMON_RESPONSE_STATUS_ERROR;
}
