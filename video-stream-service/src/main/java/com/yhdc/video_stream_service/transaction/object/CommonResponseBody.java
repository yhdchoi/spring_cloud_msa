package com.yhdc.video_stream_service.transaction.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonResponseBody {

    private String transactionId;
    private String transactionType;
    private String status;

    private List<CommonErrorBody> errors;
}
