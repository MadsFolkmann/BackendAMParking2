package org.parking.backendamparking.IntegrationsTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.parking.backendamparking.DTO.UserDTORequest;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Repository.CarsRepository;
import org.parking.backendamparking.Repository.CaseRepository;
import org.parking.backendamparking.Repository.UserRepository;
import org.parking.backendamparking.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserIntegrationsTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser1;
    private User testUser2;



    @BeforeEach
    void setUp() {
        caseRepository.deleteAll();
        carsRepository.deleteAll();
        usersRepository.deleteAll();


        testUser1 = new User();
        testUser1.setEmail("abdi@test.dk");
        testUser1.setPassword("password123");
        testUser1.setFirstName("Abdi");
        testUser1.setLastName("Mohamed");
        testUser1.setRentalUnit(1000000011L);
        testUser1.setPhoneNumber(12345678);
        testUser1.setRole(Roles.USER);
        usersRepository.save(testUser1);

        testUser2 = new User();
        testUser2.setEmail("mads@test.dk");
        testUser2.setPassword("password456");
        testUser2.setFirstName("Mads");
        testUser2.setLastName("Folkmann");
        testUser2.setRentalUnit(1000000006L);
        testUser2.setPhoneNumber(87654321);
        testUser2.setRole(Roles.ADMIN);
        usersRepository.save(testUser2);
    }


    /**
     * Integration test for getting all users.
     * @throws Exception if the request fails
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Abdi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Mads"));
    }

    /**
     * Integration test for getting a user by ID.
     * @throws Exception if the request fails
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetUserById() throws Exception {
        Long userId = testUser1.getId();
        mockMvc.perform(get("/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Abdi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Mohamed"));
    }

    /**
     * Integration test for adding a new user.
     * @throws Exception if the request fails
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddUser() throws Exception {
        UserDTORequest newUserRequest = new UserDTORequest();
        newUserRequest.setEmail("newUser@gmail.com");
        newUserRequest.setPassword("newPassword123");
        newUserRequest.setFirstName("New");
        newUserRequest.setLastName("User");
        newUserRequest.setRentalUnit(1000000012L);
        newUserRequest.setPhoneNumber(123456789);
        newUserRequest.setRole(Roles.USER);

        mockMvc.perform(post("/user/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("New"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("User"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("newUser@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(123456789))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rentalUnit").value(1000000012L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("USER"));
    }


    /**
     * Integration test for updating a user.
     * @throws Exception if the request fails
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateUser() throws Exception {
        Long userId = testUser1.getId();
        UserDTORequest updateUserRequest = new UserDTORequest();
        updateUserRequest.setFirstName("Updated");
        updateUserRequest.setLastName("Name");
        updateUserRequest.setEmail("abdi@test.dk");
        updateUserRequest.setPhoneNumber(98765432);
        updateUserRequest.setRentalUnit(1000000011L);
        updateUserRequest.setAddress("Updated Address");
        updateUserRequest.setCity("Updated City");
        updateUserRequest.setZipCode(1234);
        updateUserRequest.setRole(Roles.USER);

        mockMvc.perform(put("/user/update/" + userId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("abdi@test.dk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(98765432))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rentalUnit").value(1000000011L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Updated Address"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Updated City"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("USER"));
    }

    /**
     * Integration test for deleting a user.
     * @throws Exception if the request fails
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteUser() throws Exception {
        Long userId = testUser2.getId();

        mockMvc.perform(delete("/user/delete/" + userId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertFalse(usersRepository.existsById(userId));
    }
}
