package org.parking.backendamparking.Repository;

import org.parking.backendamparking.Entity.PArea;
import org.parking.backendamparking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PAreaRepository extends JpaRepository<PArea, Long> {
    PArea findByAreaName(String areaName);
    List<PArea> findAll();
    Optional<PArea> findById(PArea parea);


}
