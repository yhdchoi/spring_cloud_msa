package com.yhdc.ai_server.service;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);
    private final OpenAiImageModel openAiImageModel;

    public ImageService(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;
    }

    /**
     * Image generator
     *
     * @param prompt
     * @param number
     * @param height
     * @param width
     */
    public ResponseEntity<?> generateImage(String prompt, int number, int height, int width) {

        try {
            ImageResponse imageResponse = openAiImageModel.call(
                    new ImagePrompt(prompt,
                            OpenAiImageOptions.builder()
                                    .withModel("dall-e-3")
                                    .withQuality("hd")
                                    .style("natural")
                                    .withN(number)
                                    .withHeight(height)
                                    .withWidth(width)
                                    .build()
                    )
            );

            // Streams to return multiple images
            List<String> imageUrlList = imageResponse.getResults().stream()
                    .map(result -> result.getOutput().getUrl())
                    .toList();

            HashMap<String, Object> response = new HashMap<>();
            response.put("information", "WARN: Image links will be expired after 60 minutes");
            response.put("imageUrlList", imageUrlList);

            log.info("Response: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (NonTransientAiException ntae) {
            throw new RuntimeException(ntae);
        }
    }

}
