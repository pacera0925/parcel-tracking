package com.parceltracker.parceltracking.repository;

import com.parceltracker.parceltracking.domain.Parcel;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel, UUID> {

    List<Parcel> findByTrackingNumbers(Collection<String> trackingNumbers);

}
