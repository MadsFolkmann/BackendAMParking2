package org.parking.backendamparking.Service;

import jakarta.persistence.EntityNotFoundException;
import org.parking.backendamparking.DTO.UserDTORequest;
import org.parking.backendamparking.DTO.UserDTOResponse;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Repository.UserRepository;
import org.parking.backendamparking.Repository.RentalUnitRepository;
import org.parking.backendamparking.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RentalUnitRepository rentalUnitRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RentalUnitRepository rentalUnitRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rentalUnitRepository = rentalUnitRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*  Get All Users  */
    public List<UserDTOResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTOResponse::new)
                .collect(Collectors.toList());
    }

    /*  Get Specific User  */
    public UserDTOResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return new UserDTOResponse(user);
    }

    /*  Get Users by Rental Unit  */
    public List<UserDTOResponse> getUsersByRentalUnit(Long rentalUnitId) {
        if (!rentalUnitRepository.existsById(rentalUnitId)) {
            throw new EntityNotFoundException("Rental unit not found with id: " + rentalUnitId);
        }
        return userRepository.findByRentalUnit(rentalUnitId).stream()
                .map(UserDTOResponse::new)
                .collect(Collectors.toList());
    }

    /*  Add User  */
    public UserDTOResponse addUser(UserDTORequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use: " + request.getEmail());
        }

        if (!rentalUnitRepository.existsById(request.getRentalUnit())) {
            throw new EntityNotFoundException("Rental unit does not exist: " + request.getRentalUnit());
        }

        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setRentalUnit(request.getRentalUnit());
        newUser.setAddress(request.getAddress());
        newUser.setCity(request.getCity());
        newUser.setZipCode(request.getZipCode());
        newUser.setRole(Optional.ofNullable(request.getRole()).orElse(Roles.USER));

        userRepository.save(newUser);
        return new UserDTOResponse(newUser);
    }

    /*  Login User  */
    public UserDTOResponse loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No user found with email: " + email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return new UserDTOResponse(user);
    }

    /*  Update User  */
    public UserDTOResponse updateUser(Long id, UserDTORequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        // Kun opdater kodeord hvis det er angivet og anderledes
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        if (!rentalUnitRepository.existsById(request.getRentalUnit())) {
            throw new EntityNotFoundException("Rental unit does not exist: " + request.getRentalUnit());
        }
        user.setRentalUnit(request.getRentalUnit());

        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setZipCode(request.getZipCode());

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        userRepository.save(user);
        return new UserDTOResponse(user);
    }

    /*  Delete User  */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete. User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
