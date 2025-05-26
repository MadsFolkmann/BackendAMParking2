package org.parking.backendamparking.Service;

import jakarta.persistence.EntityNotFoundException;
import org.parking.backendamparking.DTO.UserDTORequest;
import org.parking.backendamparking.DTO.UserDTOResponse;
import org.parking.backendamparking.DTO.UserUpdateDTO;
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

    /** Get All Users
     * @return List of UserDTOResponse
     * This method retrieves all users from the repository and maps them to UserDTOResponse.
     */

    /*  Get All Users  */
    public List<UserDTOResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTOResponse::new)
                .collect(Collectors.toList());
    }

    /** Get Specific User
     * @param id the ID of the user to be retrieved
     * @return UserDTOResponse
     * This method retrieves a specific user by ID and maps it to UserDTOResponse.
     */

    /*  Get Specific User  */
    public UserDTOResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return new UserDTOResponse(user);
    }

    /** Get Users by Rental Unit
     * @param rentalUnitId the ID of the rental unit whose users are to be retrieved
     * @return List of UserDTOResponse
     * This method retrieves all users associated with a specific rental unit.
     */

    /*  Get Users by Rental Unit  */
    public List<UserDTOResponse> getUsersByRentalUnit(Long rentalUnitId) {
        if (!rentalUnitRepository.existsById(rentalUnitId)) {
            throw new EntityNotFoundException("Rental unit not found with id: " + rentalUnitId);
        }
        return userRepository.findByRentalUnit(rentalUnitId).stream()
                .map(UserDTOResponse::new)
                .collect(Collectors.toList());
    }

    /** Add User
     * @param request the UserDTORequest containing the details of the user to be added
     * @return UserDTOResponse
     * This method adds a new user to the repository after validating the email and rental unit.
     */

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


    /** Login User
     * @param email the email of the user trying to log in
     * @param password the password of the user trying to log in
     * @return UserDTOResponse
     * This method authenticates a user by checking their email and password.
     */

    /*  Login User  */
    public UserDTOResponse loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No user found with email: " + email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return new UserDTOResponse(user);
    }

    /** Update User
     * @param id the ID of the user to be updated
     * @param request the UserUpdateDTO containing the updated details
     * @return UserDTOResponse
     * This method updates an existing user's details.
     */

    /*  Update User  */
    public UserDTOResponse updateUser(Long id, UserUpdateDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setZipCode(request.getZipCode());

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        userRepository.save(user);
        return new UserDTOResponse(user);
    }

    /** Delete User
     * @param id the ID of the user to be deleted
     * This method deletes a user by their ID.
     * If the user does not exist, it throws an EntityNotFoundException.
     */

    /*  Delete User  */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Cannot delete. User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
