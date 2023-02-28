package com.project.hb.controller.reservation;

import com.project.hb.exception.InvalidReservationDateException;
import com.project.hb.model.reservation.Reservation;
import com.project.hb.model.user.User;
import com.project.hb.service.reservation.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "Authorization, Content-Type", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.DELETE})
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ReservationController {
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public List<Reservation> listReservations(Principal principal) {
        return reservationService.listByGuest(principal.getName());
    }

    @PostMapping("/reservations")
    public void addReservation(@RequestBody Reservation reservation, Principal principal) throws InvalidReservationDateException {
        LocalDate checkinDate = reservation.getCheckinDate();
        LocalDate checkoutDate = reservation.getCheckoutDate();
        if (checkinDate.equals(checkoutDate) || checkinDate.isAfter(checkoutDate) || checkinDate.isBefore(LocalDate.now())) {
            throw new InvalidReservationDateException("Invalid check in check out date.");
        }
        User guest = new User();
        guest.setUsername(principal.getName());
        reservation.setGuest(guest);
        reservationService.add(reservation);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId, Principal principal) {
        reservationService.delete(reservationId, principal.getName());
    }
}
