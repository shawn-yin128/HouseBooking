package com.project.hb.service.stay;

import com.project.hb.exception.StayDeleteException;
import com.project.hb.exception.StayNotExistException;
import com.project.hb.model.reservation.Reservation;
import com.project.hb.model.search.Location;
import com.project.hb.model.stay.Stay;
import com.project.hb.model.stay.StayImage;
import com.project.hb.model.user.User;
import com.project.hb.repository.reservation.ReservationRepository;
import com.project.hb.repository.reservation.StayReservationDateRepository;
import com.project.hb.repository.search.LocationRepository;
import com.project.hb.repository.stay.StayRepository;
import com.project.hb.service.search.GeoCodingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class StayService {
    private StayRepository stayRepository;
    private LocationRepository locationRepository;
    private ReservationRepository reservationRepository;
    private StayReservationDateRepository stayReservationDateRepository;
    private ImageStorageService imageStorageService;
    private GeoCodingService geoCodingService;

    public void addStay(Stay stay, MultipartFile[] images) {
        List<String> links = Arrays.stream(images)
                .parallel()
                .map(image -> imageStorageService.upload(image))
                .toList();
        List<StayImage> stayImages = new ArrayList<>();
        for (String link : links) {
            stayImages.add(new StayImage(link, stay));
        }
        stay.setImages(stayImages);
        stayRepository.save(stay);
        Location location = geoCodingService.getLatLon(stay.getId(), stay.getAddress());
        locationRepository.save(location);
    }

    public void addStay(Stay stay) {
        stayRepository.save(stay);
    }

    public List<Stay> findStayByHost(String username) {
        User host = new User();
        host.setUsername(username);
        return stayRepository.findByHost(host);
    }

    public Stay findStayByIdAndHost(Long stayId, String username) throws StayNotExistException {
        User host = new User();
        host.setUsername(username);
        Stay stay = stayRepository.findByIdAndHost(stayId, host);
        if (stay == null) {
            throw new StayNotExistException("Stay not exists.");
        }
        return stay;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteStayByIdAndHost(Long stayId, String username) throws StayNotExistException, StayDeleteException {
        User host = new User();
        host.setUsername(username);
        Stay stay = stayRepository.findByIdAndHost(stayId, host);
        if (stay == null) {
            throw new StayNotExistException("Stay not exists.");
        }
        List<Reservation> existedReservation = reservationRepository.findByStayAndCheckoutDateAfter(stay, LocalDate.now());
        if (existedReservation != null && !existedReservation.isEmpty()) {
            throw new StayDeleteException("Cannot delete stay.");
        }
        stayRepository.deleteById(stayId);
    }
}
