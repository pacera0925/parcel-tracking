package com.parceltracker.parceltracking.service;

import static com.parceltracker.parceltracking.domain.MessageIntent.PARCEL_STATUS_INQUIRY;

import com.parceltracker.parceltracking.domain.MessageIntent;
import com.parceltracker.parceltracking.dto.UserInput;
import com.parceltracker.parceltracking.exception.ChatServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final ParcelService parcelService;
    private final OpenAiService openAiService;

    @Autowired
    public ChatService(ParcelService parcelService, OpenAiService openAiService) {
        this.parcelService = parcelService;
        this.openAiService = openAiService;
    }

    public String processChatInput(UserInput userInput) {
        try {
            String userMessage = userInput.getMessage();
            MessageIntent intent = callIdentifyMessageIntent(userMessage);

            switch (intent) {
                case PARCEL_STATUS_INQUIRY:
                    return "a";
                case UNSUPPORTED_REQUEST:
                    return "b";
                case GENERIC_RESPONSE:
                default:
                    return "c";
            }

        } catch (Exception e) {
            logger.error("Something went wrong {}", e.getMessage(), e);
            throw new ChatServiceException("Failed to process message.");
        }
    }

    private MessageIntent callIdentifyMessageIntent(String userMessage) {
        return PARCEL_STATUS_INQUIRY;
    }


}
