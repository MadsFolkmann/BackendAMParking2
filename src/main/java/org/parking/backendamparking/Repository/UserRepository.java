package org.parking.backendamparking.Repository;

import org.parking.backendamparking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    List<User> findByRentalUnit(Long rentalUnit);
    Optional<User> findByEmail(String email);
    Optional<User> findById(User user);
    boolean existsByRentalUnit(Long rentalUnit);
}
