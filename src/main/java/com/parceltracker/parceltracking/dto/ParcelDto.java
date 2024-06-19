package com.parceltracker.parceltracking.dto;

import com.parceltracker.parceltracking.domain.Parcel;
import com.parceltracker.parceltracking.domain.ParcelStatus;
import com.parceltracker.parceltracking.domain.SenderRecipientInfo;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class ParcelDto {

    private String trackingNumber;
    private SenderRecipientInfo sender;
    private SenderRecipientInfo receiver;
    private LocalDateTime shippingDateTime;
    private LocalDateTime deliveryDateTime;
    private ParcelStatus status;

    public static ParcelDto buildFromParcel(Parcel parcel) {
        ParcelDto dto = new ParcelDto();
        dto.setTrackingNumber(parcel.getTrackingNumber());
        dto.setSender(parcel.getSender());
        dto.setReceiver(parcel.getReceiver());
        dto.setShippingDateTime(parcel.getShippingDateTime());
        dto.setDeliveryDateTime(parcel.getDeliveryDateTime());
        dto.setStatus(parcel.getStatus());
        return dto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParcelDto parcelDto = (ParcelDto) o;
        return trackingNumber.equals(parcelDto.trackingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ParcelDto.class.getSimpleName() + "[", "]")
            .add("trackingNumber='" + trackingNumber + "'")
            .add("sender=" + sender)
            .add("receiver=" + receiver)
            .add("status=" + status)
            .toString();
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void setSender(SenderRecipientInfo sender) {
        this.sender = sender;
    }

    public void setReceiver(SenderRecipientInfo receiver) {
        this.receiver = receiver;
    }

    public void setShippingDateTime(LocalDateTime shippingDateTime) {
        this.shippingDateTime = shippingDateTime;
    }

    public void setDeliveryDateTime(LocalDateTime deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public void setStatus(ParcelStatus status) {
        this.status = status;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public SenderRecipientInfo getSender() {
        return sender;
    }

    public SenderRecipientInfo getReceiver() {
        return receiver;
    }

    public LocalDateTime getShippingDateTime() {
        return shippingDateTime;
    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public ParcelStatus getStatus() {
        return status;
    }

}
