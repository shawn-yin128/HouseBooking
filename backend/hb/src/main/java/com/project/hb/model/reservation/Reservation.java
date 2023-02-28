package com.project.hb.model.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.hb.model.stay.Stay;
import com.project.hb.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty("checkin_date")
    private LocalDate checkinDate;
    @JsonProperty("checkout_date")
    private LocalDate checkoutDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User guest;
    @ManyToOne
    @JoinColumn(name = "stay_id")
    private Stay stay;
}
