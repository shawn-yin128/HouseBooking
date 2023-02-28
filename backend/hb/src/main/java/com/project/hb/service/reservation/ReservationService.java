package com.project.hb.service.reservation;

import com.project.hb.exception.ReservationCollisionException;
import com.project.hb.exception.ReservationNotFoundException;
import com.project.hb.model.reservation.Reservation;
import com.project.hb.model.stay.Stay;
import com.project.hb.model.stay.StayReservedDate;
import com.project.hb.model.stay.StayReservedDateKey;
import com.project.hb.model.user.User;
import com.project.hb.repository.reservation.ReservationRepository;
import com.project.hb.repository.reservation.StayReservationDateRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ReservationService {
    private ReservationRepository reservationRepository;
    private StayReservationDateRepository stayReservationDateRepository;

    public List<Reservation> listByGuest(String username) {
        User guest = new User();
        guest.setUsername(username);
        return reservationRepository.findByGuest(guest);
    }

    public List<Reservation> listByStay(Long stayId) {
        Stay stay = new Stay();
        stay.setId(stayId);
        return reservationRepository.findByStay(stay);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(Reservation reservation) throws ReservationCollisionException {
        Set<Long> stayIds = stayReservationDateRepository.findByIdAndDateBetween(Collections.singletonList(reservation.getStay().getId()), reservation.getCheckinDate(), reservation.getCheckoutDate().minusDays(1));
        if (!stayIds.isEmpty()) {
            throw new ReservationCollisionException("Reservation time already exists.");
        }
        List<StayReservedDate> reservedDates = new ArrayList<>();
        for (LocalDate date = reservation.getCheckinDate(); date.isBefore(reservation.getCheckoutDate()); date = date.plusDays(1)) {
            reservedDates.add(new StayReservedDate(new StayReservedDateKey(reservation.getStay().getId(), date), reservation.getStay()));
        }
        stayReservationDateRepository.saveAll(reservedDates);
        reservationRepository.save(reservation);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(Long reservationId, String username) throws ReservationNotFoundException {
        User guest = new User();
        guest.setUsername(username);
        Reservation reservation = reservationRepository.findByIdAndGuest(reservationId, guest);
        if (reservation == null) {
            throw new ReservationNotFoundException("Reservation not found.");
        }
        for (LocalDate date = reservation.getCheckinDate(); date.isBefore(reservation.getCheckoutDate()); date = date.plusDays(1)) {
            stayReservationDateRepository.deleteById(new StayReservedDateKey(reservation.getStay().getId(), date));
        }
        reservationRepository.deleteById(reservationId);
    }
}
