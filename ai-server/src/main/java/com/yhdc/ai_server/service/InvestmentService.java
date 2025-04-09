package com.yhdc.ai_server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class InvestmentService {

    private final ChatModel chatModel;


    public ResponseEntity<?> getInvestmentAdvice(String market) {
        try {
            var template = """
                    What is the latest trends in investment landscape.
                    I want the latest stock market news for: {market}.
                    """;

            PromptTemplate promptTemplate = new PromptTemplate(template);
            Map<String, Object> params = Map.of("market", market);
            Prompt prompt = promptTemplate.create(params);

            final String response = chatModel.call(prompt).getResult().getOutput().getText();
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
