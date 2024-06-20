package com.parceltracker.parceltracking.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Parcel extends VersionedEntity {

    @Column(unique = true, nullable = false)
    private String trackingNumber;

    @Embedded
    @Column(nullable = false)
    @AttributeOverride(name = "name", column = @Column(name = "sender_name"))
    @AttributeOverride(name = "address", column = @Column(name = "sender_address"))
    private SenderRecipientInfo sender;

    @Embedded
    @Column(nullable = false)
    @AttributeOverride(name = "name", column = @Column(name = "receiver_name"))
    @AttributeOverride(name = "address", column = @Column(name = "receiver_address"))
    private SenderRecipientInfo receiver;

    @Column
    private LocalDateTime shippingDateTime;

    @Column
    private LocalDateTime deliveryDateTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ParcelStatus status;

    @Column
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Parcel parcel = (Parcel) o;
        return trackingNumber.equals(parcel.trackingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), trackingNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Parcel.class.getSimpleName() + "[", "]")
            .add("trackingNumber='" + trackingNumber + "'")
            .add("sender=" + sender)
            .add("receiver=" + receiver)
            .add("status=" + status)
            .toString();
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public SenderRecipientInfo getSender() {
        return sender;
    }

    public void setSender(SenderRecipientInfo sender) {
        this.sender = sender;
    }

    public SenderRecipientInfo getReceiver() {
        return receiver;
    }

    public void setReceiver(SenderRecipientInfo receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getShippingDateTime() {
        return shippingDateTime;
    }

    public void setShippingDateTime(LocalDateTime shippingDateTime) {
        this.shippingDateTime = shippingDateTime;
    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(LocalDateTime deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public void setStatus(ParcelStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
