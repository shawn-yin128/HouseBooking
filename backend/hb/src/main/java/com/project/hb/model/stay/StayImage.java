package com.project.hb.model.stay;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stay_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StayImage {
    @Id
    private String url;
    @ManyToOne
    @JoinColumn(name = "stay_id")
    @JsonIgnore
    private Stay stay;
}
