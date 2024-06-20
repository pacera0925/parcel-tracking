package com.parceltracker.parceltracking.repository;

import com.parceltracker.parceltracking.domain.Parcel;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParcelRepository extends JpaRepository<Parcel, UUID> {

    @Query("SELECT parcel FROM Parcel parcel WHERE parcel.trackingNumber IN (:trackingNumbers)")
    List<Parcel> findByTrackingNumber(@Param("trackingNumbers") Collection<String> trackingNumbers);

}
