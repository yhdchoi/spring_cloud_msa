package com.yhdc.ai_server.controller;


import com.yhdc.ai_server.dto.ChatRequestRecord;
import com.yhdc.ai_server.service.ChatService;
import com.yhdc.ai_server.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {

    private final ChatService chatService;
    private final ImageService imageService;

    public AiController(ChatService chatService, ImageService imageService) {
        this.chatService = chatService;
        this.imageService = imageService;
    }


    /**
     * Custom chat
     *
     * @param chatRequestRecord
     */
    @GetMapping("/open-ai/chat-ai")
    public ResponseEntity<?> chatResponse(@RequestBody ChatRequestRecord chatRequestRecord) {
        return chatService.getChatResponse(chatRequestRecord);
    }


    /**
     * Generate image
     *
     * @param prompt
     * @apiNote
     */
    @GetMapping("/open-ai/gen-image")
    public ResponseEntity<?> imageGenerator(@RequestParam(value = "prompt") String prompt,
                                            @RequestParam(value = "number", required = false, defaultValue = "1") String number,
                                            @RequestParam(value = "height", required = false, defaultValue = "1024") String height,
                                            @RequestParam(value = "width", required = false, defaultValue = "1024") String width) {
        return imageService.generateImage(prompt,
                Integer.parseInt(number),
                Integer.parseInt(height),
                Integer.parseInt(width));
    }


}
