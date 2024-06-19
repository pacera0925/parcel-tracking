package com.parceltracker.parceltracking.service;

import com.parceltracker.parceltracking.domain.Parcel;
import com.parceltracker.parceltracking.dto.ParcelDto;
import com.parceltracker.parceltracking.exception.ParcelNotFoundException;
import com.parceltracker.parceltracking.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParcelService {

    private final ParcelRepository parcelRepository;

    @Autowired
    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    public ParcelDto findByTrackingNumber(String trackingNumber) {
        Parcel parcel = parcelRepository.findByTrackingNumber(trackingNumber)
            .orElseThrow(() -> new ParcelNotFoundException(trackingNumber));

        return ParcelDto.buildFromParcel(parcel);
    }
}
