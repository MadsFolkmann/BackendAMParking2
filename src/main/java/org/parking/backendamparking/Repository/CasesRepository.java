package org.parking.backendamparking.Repository;

import org.parking.backendamparking.Entity.Cases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CasesRepository extends JpaRepository<Cases, Long> {
    Optional<Cases> findById(Long id);

}
