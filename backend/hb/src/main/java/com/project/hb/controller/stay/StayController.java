package com.project.hb.controller.stay;

import com.project.hb.model.reservation.Reservation;
import com.project.hb.model.stay.Stay;
import com.project.hb.model.user.User;
import com.project.hb.service.reservation.ReservationService;
import com.project.hb.service.stay.StayService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "Authorization, Content-Type", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.DELETE})
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class StayController {
    private StayService stayService;
    private ReservationService reservationService;

    @PostMapping("/stays")
    public void addStay(@RequestParam("name") String name,
                        @RequestParam("address") String address,
                        @RequestParam("description") String description,
                        @RequestParam("guest_number") int guestNumber,
                        @RequestParam("images") MultipartFile[] images,
                        Principal principal) {
        User hostUser = new User();
        hostUser.setUsername(principal.getName());
        Stay stay = new Stay();
        stay.setName(name);
        stay.setAddress(address);
        stay.setDescription(description);
        stay.setHost(hostUser);
        stay.setGuestNumber(guestNumber);
        stayService.addStay(stay, images);
    }

    @DeleteMapping("/stays/{stayId}")
    public void deleteStay(@PathVariable Long stayId,
                           Principal principal) {
        stayService.deleteStayByIdAndHost(stayId, principal.getName());
    }

    @GetMapping("/stays")
    public List<Stay> getStay(Principal principal) {
        return stayService.findStayByHost(principal.getName());
    }

    @GetMapping("/stays/{stayId}")
    public Stay getStay(@PathVariable Long stayId,
                        Principal principal) {
        return stayService.findStayByIdAndHost(stayId, principal.getName());
    }

    @GetMapping("/stays/reservations/{stayId}")
    public List<Reservation> getReservations(@PathVariable Long stayId) {
        return reservationService.listByStay(stayId);
    }
}
