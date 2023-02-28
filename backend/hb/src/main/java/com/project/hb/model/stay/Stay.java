package com.project.hb.model.stay;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.hb.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "stay")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String address;
    @JsonProperty(value = "guest_number")
    private int guestNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User host;
    @JsonIgnore
    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StayReservedDate> reservedDate;
    @OneToMany(mappedBy = "stay", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StayImage> images;
}
