package com.project.hb.repository.stay;

import com.project.hb.model.stay.Stay;
import com.project.hb.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {
    List<Stay> findByHost(User user);
    Stay findByIdAndHost(Long id, User user);
    List<Stay> findByIdInAndGuestNumberGreaterThanEqual(List<Long> stayIds, int guestNumber);
}
