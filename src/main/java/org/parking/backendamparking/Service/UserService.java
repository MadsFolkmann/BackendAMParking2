package org.parking.backendamparking.Service;

import org.parking.backendamparking.DTO.UserDTORequest;
import org.parking.backendamparking.DTO.UserDTOResponse;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*  Get All Users  */
    public List<UserDTOResponse> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .map(UserDTOResponse::new)
                .collect(Collectors.toList());
    }

    /*  Get Specific User  */
    public UserDTOResponse getUserById(Long id) {
        return new UserDTOResponse(userRepository.findById(id).orElseThrow());
    }

    /*  Get Users by Rental Unit  */
    public List<UserDTOResponse> getUsersByRentalUnit(Long rentalUnit) {
        List<User> users = userRepository.findByRentalUnit(rentalUnit);
        return users.stream()
                .map(UserDTOResponse::new)
                .collect(Collectors.toList());
    }

    /* Add User */
    public UserDTOResponse addUser(UserDTORequest request) {
        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(hashedPassword);

        newUser.setEmail(request.getEmail());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setRentalUnit(request.getRentalUnit());
        newUser.setAdress(request.getAdress());
        newUser.setCity(request.getCity());
        newUser.setZipCode(request.getZipCode());

        userRepository.save(newUser);
        return new UserDTOResponse(newUser);
    }

    /* Login User */
    public UserDTOResponse loginUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow();
        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UserDTOResponse(user);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }


    /* Update User */
    public UserDTOResponse updateUser(Long id, UserDTORequest request) {
        User user = userRepository.findById(id).orElseThrow();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        if (!request.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRentalUnit(request.getRentalUnit());
        user.setAdress(request.getAdress());
        user.setCity(request.getCity());
        user.setZipCode(request.getZipCode());

        userRepository.save(user);
        return new UserDTOResponse(user);
    }

    /* Delete User */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
