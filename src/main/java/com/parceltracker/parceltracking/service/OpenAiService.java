package com.parceltracker.parceltracking.service;

import com.parceltracker.parceltracking.domain.MessageIntent;
import com.parceltracker.parceltracking.dto.ParcelDto;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiService {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiService.class);

    private final String url;
    private final String key;
    private final String model;
    private final RestTemplate restTemplate;
    private final OpenAiPromptService openAiPromptService;

    @Autowired
    public OpenAiService(@Value("${openai.api.url}") String url, @Value("${openai.api.key}") String key,
        @Value("${openai.model}") String model, RestTemplate restTemplate, OpenAiPromptService openAiPromptService) {
        this.key = key;
        this.url = url;
        this.model = model;
        this.restTemplate = restTemplate;
        this.openAiPromptService = openAiPromptService;
    }


    public MessageIntent identifyMessageIntent(String userMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + key);
        headers.set("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("messages", openAiPromptService.generateIntentMessagesParam(userMessage));
        requestBody.put("max_tokens", 10);

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        JSONObject responseBody = new JSONObject(response.getBody());
        String intent = responseBody.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
            .getString("content").trim();

        logger.debug("Parsed intent: {}", intent);
        return MessageIntent.getEnumValue(intent);
    }


    public String getParcelStatusInquiryResponse(Set<String> trackingNumbers, List<ParcelDto> parcels) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + key);
        headers.set("Content-Type", "application/json");

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("messages", openAiPromptService.generateStatusInquiryPrompt(trackingNumbers, parcels));

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        JSONObject responseBody = new JSONObject(response.getBody());
        String chatResponse = responseBody.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
            .getString("content").trim();

        logger.debug("Response: {}", chatResponse);
        return chatResponse;
    }
}
