package com.parceltracker.parceltracking.service;

import com.parceltracker.parceltracking.dto.ParcelDto;
import java.util.Collection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class OpenAiPromptService {

    private static final String CHAT_BOT_INTENT_CONTEXT = "You are a helpful parcel tracking assistant chat bot. You analyze the user message and "
        + "categorize its intent if it is PARCEL_STATUS_INQUIRY, UNSUPPORTED_REQUEST, GENERIC_RESPONSE. You also know that it is a strict rule that "
        + "PARCEL_STATUS_INQUIRY must have at least ONE tracking number with format of 'TRK*' (TRK followed by numbers). "
        + "Your response is limited to one of the following: PARCEL_STATUS_INQUIRY, UNSUPPORTED_REQUEST, GENERIC_RESPONSE.";

    private static final String CHAT_BOT_STATUS_UPDATE_CONTEXT = "You are an assistant chat bot that provides updates on parcel status. "
        + "Respond with a friendly and informative message based on the given parcel data retrieved from the system. You will also provide "
        + "a rough estimate of delivery date on parcel with status of IN_TRANSIT. The estimate is based on the distance of the sender address "
        + "to its receiver address. Only provide estimate if possible and provide it directly without mentioning other stuff related to estimate logic. "
        + "If there is no record for the tracking number, mention it as well.";

    private static final String USER_STATUS_UPDATE_CONTEXT = "The user is asking for updates on the following tracking numbers: %s "
        + "Here are the only details found in the system record: %s";

    public JSONArray generateIntentMessagesParam(String userMessage) {
        JSONArray messagesArray = new JSONArray();

        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", CHAT_BOT_INTENT_CONTEXT);
        messagesArray.put(systemMessage);

        JSONObject userMessageObj = new JSONObject();
        userMessageObj.put("role", "user");
        userMessageObj.put("content", userMessage);
        messagesArray.put(userMessageObj);

        return messagesArray;
    }

    public JSONArray generateStatusInquiryPrompt(Collection<String> trackingNumbers, Collection<ParcelDto> parcels) {
        JSONArray messagesArray = new JSONArray();

        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", CHAT_BOT_STATUS_UPDATE_CONTEXT);
        messagesArray.put(systemMessage);

        JSONObject userMessageObj = new JSONObject();
        userMessageObj.put("role", "user");
        userMessageObj.put("content", String.format(USER_STATUS_UPDATE_CONTEXT, trackingNumbers, parcels));
        messagesArray.put(userMessageObj);

        return messagesArray;
    }
}
