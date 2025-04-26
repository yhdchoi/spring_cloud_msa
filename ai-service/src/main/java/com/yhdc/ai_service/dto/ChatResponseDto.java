package com.yhdc.ai_service.dto;

import lombok.Data;

@Data
public class ChatResponseDto {

    private String textResponse;

    private String mediaName;
    private String mimeType;
    private byte[] mediaData;

    private String message;

}
