package com.parceltracker.parceltracking.dto;

import com.parceltracker.parceltracking.domain.ParcelMother;

public class ParcelDtoMother {
    public static ParcelDto trk0001() {
        return ParcelDto.buildFromParcel(ParcelMother.trk0001());
    }

    public static ParcelDto trk0002() {
        return ParcelDto.buildFromParcel(ParcelMother.trk0002());
    }

}
