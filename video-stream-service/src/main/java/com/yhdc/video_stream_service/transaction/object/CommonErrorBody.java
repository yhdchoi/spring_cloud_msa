package com.yhdc.video_stream_service.transaction.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonErrorBody {

    private String id;
    private String message;

}
