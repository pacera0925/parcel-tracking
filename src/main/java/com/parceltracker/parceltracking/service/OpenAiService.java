package com.parceltracker.parceltracking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAiService {

    private final String API_KEY;

    public OpenAiService(@Value("${openai.api.key}") String apiKey) {
        this.API_KEY = apiKey;
    }

    public String getAiResponse(String trackingNumber) {
        return "";
    }
}
