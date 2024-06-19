package com.parceltracker.parceltracking.exception;

public class ParcelNotFoundException extends RuntimeException {

    public ParcelNotFoundException(String trackingNumber) {
        super("No parcel found with tracking number: " + trackingNumber);
    }

}
