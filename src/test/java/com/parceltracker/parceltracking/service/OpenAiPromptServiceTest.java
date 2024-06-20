package com.parceltracker.parceltracking.service;

import static org.junit.Assert.assertEquals;

import com.parceltracker.parceltracking.dto.ParcelDto;
import com.parceltracker.parceltracking.dto.ParcelDtoMother;
import java.util.Arrays;
import java.util.Collection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OpenAiPromptServiceTest {

    private static final String CHAT_BOT_INTENT_CONTEXT =
        "You are a helpful parcel tracking assistant chat bot. You analyze the user message and "
            + "categorize its intent if it is PARCEL_STATUS_INQUIRY, UNSUPPORTED_REQUEST, GENERIC_RESPONSE. You also know that it is a strict rule that "
            + "PARCEL_STATUS_INQUIRY must have at least ONE tracking number with format of 'TRK*' (TRK followed by numbers). "
            + "Your response is limited to one of the following: PARCEL_STATUS_INQUIRY, UNSUPPORTED_REQUEST, GENERIC_RESPONSE.";

    private static final String CHAT_BOT_STATUS_UPDATE_CONTEXT =
        "You are an assistant chat bot that provides updates on parcel status. "
            + "Respond with a friendly and informative message based on the given parcel data retrieved from the system. You will also provide "
            + "a rough estimate of delivery date on parcel with status of IN_TRANSIT. The estimate is based on the distance of the sender address "
            + "to its receiver address. Only provide estimate if possible and provide it directly without mentioning other stuff related to estimate logic. "
            + "If there is no record for the tracking number, mention it as well.";

    private static final String USER_STATUS_UPDATE_CONTEXT =
        "The user is asking for updates on the following tracking numbers: %s "
            + "Here are the only details found in the system record: %s";

    @InjectMocks
    private OpenAiPromptService openAiPromptService;

    @Test
    public void generateIntentMessagesParam_returnsRequiredMessageParam() throws JSONException {
        String userMsg = "Status update for TRK0001";

        JSONArray result = openAiPromptService.generateIntentMessagesParam(userMsg);

        assertEquals(2, result.length());

        JSONObject systemMessage = result.getJSONObject(0);
        assertEquals("system", systemMessage.getString("role"));
        assertEquals(CHAT_BOT_INTENT_CONTEXT, systemMessage.getString("content"));

        JSONObject userMessageObj = result.getJSONObject(1);
        assertEquals("user", userMessageObj.getString("role"));
        assertEquals(userMsg, userMessageObj.getString("content"));
    }

    @Test
    public void generateStatusInquiryPrompt_returnsRequiredMessageParam() throws JSONException {
        Collection<String> trackingNumbers = Arrays.asList("TRK0001", "TRK0002");
        Collection<ParcelDto> parcels = Arrays.asList(ParcelDtoMother.trk0001(),
            ParcelDtoMother.trk0002()
        );

        JSONArray result = openAiPromptService.generateStatusInquiryPrompt(trackingNumbers, parcels);

        assertEquals(2, result.length());

        JSONObject systemMessage = result.getJSONObject(0);
        assertEquals("system", systemMessage.getString("role"));
        assertEquals(CHAT_BOT_STATUS_UPDATE_CONTEXT, systemMessage.getString("content"));

        JSONObject userMessageObj = result.getJSONObject(1);
        assertEquals("user", userMessageObj.getString("role"));
        String expectedUserMessageContent = String.format(USER_STATUS_UPDATE_CONTEXT, trackingNumbers, parcels);
        assertEquals(expectedUserMessageContent, userMessageObj.getString("content"));
    }

}
