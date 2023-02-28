package com.project.hb.controller.search;

import com.project.hb.exception.InvalidSearchDateException;
import com.project.hb.model.stay.Stay;
import com.project.hb.service.search.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "Authorization, Content-Type", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.DELETE})
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class SearchController {
    private SearchService searchService;

    @GetMapping("/search")
    public List<Stay> searchStays(@RequestParam("guest_number") int guestNumber,
                                  @RequestParam("checkin_date") String startDate,
                                  @RequestParam("checkout_date") String endDate,
                                  @RequestParam("lat") double lat,
                                  @RequestParam("lon") double lon,
                                  @RequestParam(value = "distance", required = false) String distance) throws InvalidSearchDateException {
        LocalDate checkinDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate checkoutDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (distance == null || distance.isEmpty()) {
            distance = "50";
        }
        if (checkinDate.equals(checkoutDate) || checkinDate.isAfter(checkoutDate) || checkinDate.isBefore(LocalDate.now())) {
            throw new InvalidSearchDateException("Invalid search date.");
        }
        return searchService.search(guestNumber, checkinDate, checkoutDate, lat, lon, distance);
    }
}
