package org.parking.backendamparking.Controller;

import org.parking.backendamparking.DTO.UserDTORequest;
import org.parking.backendamparking.DTO.UserDTOResponse;
import org.parking.backendamparking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

     @PostMapping("/add")
     public UserDTOResponse addUser(@RequestBody UserDTORequest userDTORequest) {
         return userService.addUser(userDTORequest);
     }

     @PutMapping("/update/{id}")
     public UserDTOResponse updateUser(@PathVariable Long id, @RequestBody UserDTORequest userDTORequest) {
         return userService.updateUser(id, userDTORequest);
     }

     @DeleteMapping("/delete/{id}")
     public void deleteUser(@PathVariable Long id) {
         userService.deleteUser(id);
     }


}
