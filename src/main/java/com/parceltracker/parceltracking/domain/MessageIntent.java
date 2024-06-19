package com.parceltracker.parceltracking.domain;

public enum MessageIntent {

    PARCEL_STATUS_INQUIRY("ParcelStatusInquiry"),
    UNSUPPORTED_REQUEST("UnsupportedRequest"),
    GENERIC_RESPONSE("GenericResponse");

    private final String label;

    MessageIntent(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
