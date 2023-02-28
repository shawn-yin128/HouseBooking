package com.project.hb.model.stay;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StayReservedDateKey implements Serializable {
    private Long stayId;
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StayReservedDateKey that = (StayReservedDateKey) o;
        return Objects.equals(stayId, that.stayId) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stayId, date);
    }
}
