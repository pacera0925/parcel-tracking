package com.parceltracker.parceltracking.service;

import com.parceltracker.parceltracking.domain.MessageIntent;
import com.parceltracker.parceltracking.dto.ParcelDto;
import com.parceltracker.parceltracking.dto.UserInput;
import com.parceltracker.parceltracking.exception.ChatServiceException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private static final Pattern TRACKING_NUMBER_PATTERN = Pattern.compile("\\bTRK\\d+\\b");

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
            MessageIntent intent = openAiService.identifyMessageIntent(userMessage);

            switch (intent) {
                case PARCEL_STATUS_INQUIRY:
                    Set<String> trackingNumbers = extractTrackingNumbers(userMessage);
                    List<ParcelDto> parcels = parcelService.findByTrackingNumbers(trackingNumbers);
                    return openAiService.getParcelStatusInquiryResponse(trackingNumbers, parcels);
                case UNSUPPORTED_REQUEST:
                    return "Sorry, we do not support this request. Please ask for a parcel status update by proving the tracking number (TRK####).";
                case GENERIC_RESPONSE:
                default:
                    return "I'm here to help with parcel tracking. Please provide a tracking number for status updates.";
            }
        } catch (Exception e) {
            logger.error("Something went wrong {}", e.getMessage(), e);
            throw new ChatServiceException("Failed to process message.");
        }
    }

    private Set<String> extractTrackingNumbers(String message) {
        Set<String> trackingNumbers = new HashSet<>();
        Matcher matcher = TRACKING_NUMBER_PATTERN.matcher(message.toUpperCase());

        while (matcher.find()) {
            trackingNumbers.add(matcher.group());
        }

        return trackingNumbers;
    }

}
