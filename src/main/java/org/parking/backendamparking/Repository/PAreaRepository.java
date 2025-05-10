package org.parking.backendamparking.Repository;

import org.parking.backendamparking.Entity.PArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PAreaRepository extends JpaRepository<PArea, Long> {
    PArea findByAreaName(String areaName);


}
