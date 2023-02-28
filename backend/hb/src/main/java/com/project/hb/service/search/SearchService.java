package com.project.hb.service.search;

import com.project.hb.model.search.Location;
import com.project.hb.model.stay.Stay;
import com.project.hb.repository.reservation.StayReservationDateRepository;
import com.project.hb.repository.search.LocationRepository;
import com.project.hb.repository.stay.StayRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class SearchService {
    private StayRepository stayRepository;
    private StayReservationDateRepository stayReservationDateRepository;
    private LocationRepository locationRepository;

    public List<Stay> search(int guestNumber, LocalDate checkIn, LocalDate checkOut, double lat, double lon, String distance) {
        List<Location> targetAreaStays = locationRepository.findByDistance(lat, lon, distance);
        if (targetAreaStays == null || targetAreaStays.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> ids = new ArrayList<>();
        for (Location loc : targetAreaStays) {
            ids.add(loc.getId());
        }
        Set<Long> reservedStays = stayReservationDateRepository.findByIdAndDateBetween(ids, checkIn, checkOut.minusDays(1));
        List<Long> selected = new ArrayList<>();
        for (Long id : ids) {
            if (!reservedStays.contains(id)) {
                selected.add(id);
            }
        }
        return stayRepository.findByIdInAndGuestNumberGreaterThanEqual(selected, guestNumber);
    }
}
