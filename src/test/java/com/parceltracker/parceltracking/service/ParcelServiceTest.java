package com.parceltracker.parceltracking.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.parceltracker.parceltracking.domain.ParcelMother;
import com.parceltracker.parceltracking.dto.ParcelDto;
import com.parceltracker.parceltracking.repository.ParcelRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParcelServiceTest {

    @InjectMocks
    private ParcelService parcelService;

    @Mock
    private ParcelRepository parcelRepository;

    @Test(expected = IllegalArgumentException.class)
    public void findByTrackingNumbers_emptyParam_throwsException() {
        parcelService.findByTrackingNumbers(new ArrayList<>());
    }

    @Test
    public void findByTrackingNumbers_existingParcel_returnsParcelDto() {
        Set<String> trackingNumbers = new HashSet<>(List.of("TRK0001"));

        when(parcelRepository.findByTrackingNumber(trackingNumbers)).thenReturn(List.of(ParcelMother.trk0001()));

        List<ParcelDto> results = parcelService.findByTrackingNumbers(trackingNumbers);

        assertFalse(results.isEmpty());
        assertTrue(results.stream().anyMatch(parcelDto -> parcelDto.getTrackingNumber().equals("TRK0001")));
    }

    @Test
    public void findByTrackingNumbers_parcelNotFound_returnsEmptyList() {
        Set<String> trackingNumbers = new HashSet<>(List.of("TRK0002"));

        when(parcelRepository.findByTrackingNumber(trackingNumbers)).thenReturn(List.of());

        List<ParcelDto> results = parcelService.findByTrackingNumbers(trackingNumbers);

        assertTrue(results.isEmpty());
    }

}
