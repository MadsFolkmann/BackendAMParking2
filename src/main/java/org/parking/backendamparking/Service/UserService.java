package org.parking.backendamparking.Service;

import org.parking.backendamparking.DTO.UserDTOResponse;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    /* Add User */
    

}
