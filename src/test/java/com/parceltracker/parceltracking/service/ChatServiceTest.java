package com.parceltracker.parceltracking.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.parceltracker.parceltracking.domain.MessageIntent;
import com.parceltracker.parceltracking.dto.ParcelDtoMother;
import com.parceltracker.parceltracking.dto.UserInput;
import com.parceltracker.parceltracking.exception.ChatServiceException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChatServiceTest {

    @InjectMocks
    private ChatService chatService;

    @Mock
    private ParcelService parcelService;

    @Mock
    private OpenAiService openAiService;

    private UserInput userInput;

    @Before
    public void init() {
        userInput = new UserInput();
        userInput.setMessage("track my package TRK0001");
    }

    @Test
    public void processChatInput_statusInquiry_callsExpectedService() {
        when(openAiService.identifyMessageIntent(userInput.getMessage())).thenReturn(
            MessageIntent.PARCEL_STATUS_INQUIRY);
        when(parcelService.findByTrackingNumbers(any())).thenReturn(List.of(ParcelDtoMother.trk0001()));

        chatService.processChatInput(userInput);

        verify(openAiService, times(1)).getParcelStatusInquiryResponse(any(), any());
    }

    @Test
    public void processChatInput_unsupported_returnsExpectedMessage() {
        when(openAiService.identifyMessageIntent(userInput.getMessage())).thenReturn(MessageIntent.UNSUPPORTED_REQUEST);

        String result = chatService.processChatInput(userInput);

        assertEquals(
            "Sorry, we do not support this request. Please ask for a parcel status update by providing the tracking number (TRK####).",
            result);
        verify(openAiService, times(0)).getParcelStatusInquiryResponse(any(), any());
    }

    @Test
    public void processChatInput_genericOrDefault_returnsExpectedMessage() {
        when(openAiService.identifyMessageIntent(userInput.getMessage())).thenReturn(MessageIntent.GENERIC_RESPONSE);

        String result = chatService.processChatInput(userInput);

        assertEquals(
            "I'm always happy to assist you in parcel tracking. Please don't hesitate in providing the tracking number.",
            result);
        verify(openAiService, times(0)).getParcelStatusInquiryResponse(any(), any());
    }

    @Test(expected = ChatServiceException.class)
    public void processChatInput_hasError_throwsException() {
        when(openAiService.identifyMessageIntent(userInput.getMessage())).thenThrow(new IllegalArgumentException());

        chatService.processChatInput(userInput);
    }

}
