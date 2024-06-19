package com.parceltracker.parceltracking.domain;

public enum ParcelStatus {

    PENDING("Pending"),
    SHIPPED("Shipped"),
    IN_TRANSIT("In Transit"),
    DELIVERED("Delivered");

    private final String label;

    ParcelStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
