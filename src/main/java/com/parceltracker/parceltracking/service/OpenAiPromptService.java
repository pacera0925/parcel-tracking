package com.parceltracker.parceltracking.service;

import com.parceltracker.parceltracking.dto.ParcelDto;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public class OpenAiPromptService {

    public String generateIntentPrompt(String userMessage) {
        return "Classify the intent of this message: " + userMessage
            + ". Possible intents: PARCEL_STATUS_INQUIRY, UNSUPPORTED_REQUEST, GENERIC_RESPONSE"
            + ". Note: Intent for PARCEL_STATUS_INQUIRY must include a tracking number of format 'TRK*' (TRK followed by numbers).";
    }

    public String generateStatusInquiryPrompt(Collection<String> trackingNumbers, Collection<ParcelDto> parcels) {
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Provide a status update for the following tracking numbers:\n");

        for (String trackingNumber : trackingNumbers) {
            promptBuilder.append(String.format("- %s: ", trackingNumber));
            ParcelDto parcel = parcels.stream()
                .filter(p -> trackingNumber.equals(p.getTrackingNumber()))
                .findFirst()
                .orElse(null);

            if (parcel != null) {
                promptBuilder.append(
                    String.format("Status: %s, Description: %s, SenderAddress: %s, ReceiverAddress: %s\n",
                        parcel.getStatus(), parcel.getDescription(), parcel.getSenderAddress(), parcel.getReceiverAddress()));
            } else {
                promptBuilder.append("No information available.\n");
            }
        }

        promptBuilder.append("If status is IN_TRANSIT, calculate rough estimate of delivery date based from sender and receiver addresses.\n");

        return promptBuilder.toString();
    }
}
