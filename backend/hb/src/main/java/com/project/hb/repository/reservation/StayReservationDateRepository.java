package com.project.hb.repository.reservation;

import com.project.hb.model.stay.StayReservedDate;
import com.project.hb.model.stay.StayReservedDateKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface StayReservationDateRepository extends JpaRepository<StayReservedDate, StayReservedDateKey> {
    @Query(value = "SELECT s.id.stayId FROM StayReservedDate AS s WHERE s.id.stayId IN ?1 AND s.id.date BETWEEN ?2 AND ?3 GROUP BY s.id.stayId")
    Set<Long> findByIdAndDateBetween(List<Long> stayIds, LocalDate startDate, LocalDate endDate);
}
