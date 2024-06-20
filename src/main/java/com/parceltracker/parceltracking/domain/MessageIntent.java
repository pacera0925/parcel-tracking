package com.parceltracker.parceltracking.domain;

public enum MessageIntent {

    PARCEL_STATUS_INQUIRY,
    UNSUPPORTED_REQUEST,
    GENERIC_RESPONSE;

    public static MessageIntent getEnumValue(String intent) {
        try {
            return MessageIntent.valueOf(intent);
        } catch (IllegalArgumentException e) {
            return GENERIC_RESPONSE;
        }
    }
}
