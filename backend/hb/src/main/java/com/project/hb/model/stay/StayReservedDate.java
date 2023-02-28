package com.project.hb.model.stay;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stay_reserved_date")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StayReservedDate {
    @EmbeddedId
    private StayReservedDateKey id;
    @ManyToOne
    @MapsId(value = "stayId")
    @JoinColumn(name = "stay_id")
    private Stay stay;
}
