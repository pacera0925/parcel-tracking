package com.parceltracker.parceltracking.service;

import com.parceltracker.parceltracking.domain.Parcel;
import com.parceltracker.parceltracking.dto.ParcelDto;
import com.parceltracker.parceltracking.exception.ParcelNotFoundException;
import com.parceltracker.parceltracking.repository.ParcelRepository;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParcelService {

    private final ParcelRepository parcelRepository;

    @Autowired
    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    public List<ParcelDto> findByTrackingNumbers(Collection<String> trackingNumbers) {
        Validate.notEmpty(trackingNumbers);

        List<Parcel> parcels= parcelRepository.findByTrackingNumbers(trackingNumbers);

        return ParcelDto.buildFromParcels(parcels);
    }
}
