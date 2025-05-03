package org.parking.backendamparking.Repository;

import org.parking.backendamparking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    List<User> findByLejemaal(String lejemaal);
    Optional<User> findByEmail(String email);
}
