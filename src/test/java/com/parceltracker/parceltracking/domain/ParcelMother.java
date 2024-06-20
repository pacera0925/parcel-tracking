package com.parceltracker.parceltracking.domain;

import java.util.UUID;
import org.springframework.test.util.ReflectionTestUtils;

public class ParcelMother {

    public static Parcel trk0001() {
        Parcel parcel = new Parcel();
        ReflectionTestUtils.setField(parcel, "id", UUID.fromString("8ede1b91-f3e9-49a3-aacd-1a243e65e9fa"));
        parcel.setTrackingNumber("TRK0001");
        parcel.setDescription("Keyboard");

        return parcel;
    }

    public static Parcel trk0002() {
        Parcel parcel = new Parcel();
        ReflectionTestUtils.setField(parcel, "id", UUID.fromString("fb828af3-2191-4594-adae-35c11482ffbc"));
        parcel.setTrackingNumber("TRK0002");
        parcel.setDescription("Monitor");

        return parcel;
    }
}
