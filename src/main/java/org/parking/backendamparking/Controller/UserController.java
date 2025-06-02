package org.parking.backendamparking.Controller;

import jakarta.validation.Valid;
import org.parking.backendamparking.DTO.LoginRequest;
import org.parking.backendamparking.DTO.UserDTORequest;
import org.parking.backendamparking.DTO.UserDTOResponse;
import org.parking.backendamparking.DTO.UserUpdateDTO;
import org.parking.backendamparking.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
     public List<UserDTOResponse> getAllUsers() {
         return userService.getAllUsers();
     }

     @GetMapping("/{id}")
        public UserDTOResponse getUserById(@PathVariable Long id) {
            return userService.getUserById(id);
        }

        @GetMapping("/lejemaal/{lejemaal}")
        public List<UserDTOResponse> getUsersByLejemaal(@PathVariable Long lejemaal) {
            return userService.getUsersByRentalUnit(lejemaal);
        }

     @PostMapping("/add")
     public UserDTOResponse addUser(@Valid @RequestBody UserDTORequest userDTORequest) {
         return userService.addUser(userDTORequest);
     }

    @PostMapping("/login")
    public UserDTOResponse loginUser(@RequestBody LoginRequest request) {
        return userService.loginUser(request.getEmail(), request.getPassword());
    }


    @PutMapping("/update/{id}")
     public UserDTOResponse updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
         return userService.updateUser(id, userUpdateDTO);
     }

     @DeleteMapping("/delete/{id}")
     public void deleteUser(@PathVariable Long id) {
         userService.deleteUser(id);
     }


}
