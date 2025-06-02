package org.parking.backendamparking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.parking.backendamparking.Controller.UserController;
import org.parking.backendamparking.DTO.UserDTOResponse;
import org.parking.backendamparking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<UserDTOResponse> userResponses;

    @BeforeEach
    void setUp() {
        UserDTOResponse user1 = new UserDTOResponse();
        user1.setId(1L);
        user1.setFirstName("Ox");
        user1.setLastName("Test");
        user1.setEmail("OxTest@Test.com");
        user1.setPhoneNumber(12345678);
        user1.setRentalUnit(1000000020L);
        user1.setAddress("Test vej");
        user1.setCity("Rødovre");
        user1.setZipCode(2610);
        user1.setRole(Roles.USER);

        UserDTOResponse user2 = new UserDTOResponse();
        user2.setId(2L);
        user2.setFirstName("Ronaldo");
        user2.setLastName("Jr");
        user2.setEmail("RJR@Test.com");
        user2.setPhoneNumber(87654321);
        user2.setRentalUnit(1000000021L);
        user2.setAddress("Almeria vej");
        user2.setCity("Madeira");
        user2.setZipCode(2000);
        user2.setRole(Roles.ADMIN);

        userResponses = Arrays.asList(user1, user2);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(userResponses);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Ox"))
                .andExpect(jsonPath("$[0].lastName").value("Test"))
                .andExpect(jsonPath("$[0].email").value("OxTest@Test.com"))
                .andExpect(jsonPath("$[1].firstName").value("Ronaldo"))
                .andExpect(jsonPath("$[1].role").value("ADMIN"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllUsersEmptyList() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetUserById() throws Exception {
        UserDTOResponse user = userResponses.get(0);
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Ox"))
                .andExpect(jsonPath("$.lastName").value("Test"))
                .andExpect(jsonPath("$.email").value("OxTest@Test.com"))
                .andExpect(jsonPath("$.phoneNumber").value(12345678))
                .andExpect(jsonPath("$.rentalUnit").value(1000000020))
                .andExpect(jsonPath("$.address").value("Test vej"))
                .andExpect(jsonPath("$.city").value("Rødovre"))
                .andExpect(jsonPath("$.zipCode").value(2610))
                .andExpect(jsonPath("$.role").value("USER"));
    }



    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetUsersByLejemaal() throws Exception {
        when(userService.getUsersByRentalUnit(1000000020L)).thenReturn(userResponses);

        mockMvc.perform(get("/user/lejemaal/1000000020"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Ox"))
                .andExpect(jsonPath("$[1].firstName").value("Ronaldo"));
    }

    /**
     * Test for getting users by lejemaal (rental unit) that does not exist.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetUsersByLejemaalNotFound() throws Exception {
        when(userService.getUsersByRentalUnit(9999999999L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/user/lejemaal/9999999999"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    /**
     * Test for adding a new user.
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    void testAddUser() throws Exception {
        UserDTOResponse newUser = new UserDTOResponse();
        newUser.setId(1L);
        newUser.setFirstName("newUserTest");
        newUser.setLastName("Test");
        newUser.setEmail("newusertest@gmail.com");
        newUser.setPhoneNumber(12345678);
        newUser.setRentalUnit(1000000020L);
        newUser.setAddress("Testvej 1");
        newUser.setCity("Rødovre");
        newUser.setZipCode(2610);
        newUser.setRole(Roles.USER);

        when(userService.addUser(Mockito.any())).thenReturn(newUser);

        String userJson = """
                {
                    "firstName": "newUserTest",
                    "lastName": "Test",
                    "email": "newusertest@gmail.com",
                    "phoneNumber": 12345678,
                    "rentalUnit": 1000000020,
                    "address": "Testvej 1",
                    "city": "Rødovre",
                    "zipCode": 2610,
                    "role": "USER"
                }
            """;

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("newUserTest"))
                .andExpect(jsonPath("$.lastName").value("Test"))
                .andExpect(jsonPath("$.email").value("newusertest@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value(12345678))
                .andExpect(jsonPath("$.rentalUnit").value(1000000020L))
                .andExpect(jsonPath("$.address").value("Testvej 1"))
                .andExpect(jsonPath("$.city").value("Rødovre"))
                .andExpect(jsonPath("$.zipCode").value(2610))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    /**
     * Test for adding a new user with invalid data.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAddUserWithInvalidData() throws Exception {
        String invalidUserJson = """
                {
                    "firstName": "",
                    "lastName": "Test",
                    "email": "invalidemail",
                    "phoneNumber": 12345678,
                    "rentalUnit": 1000000020,
                    "address": "Testvej 1",
                    "city": "Rødovre",
                    "zipCode": 2610,
                    "role": "USER"
                }
            """;

        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserJson)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    // test for updating a user

    /**
     * Test for updating a user.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateUser() throws Exception {
        UserDTOResponse updatedUser = new UserDTOResponse();
        updatedUser.setId(1L);
        updatedUser.setFirstName("OxUpdated");
        updatedUser.setLastName("UpdatedOx123");
        updatedUser.setEmail("Ox@gmail.com");
        updatedUser.setPhoneNumber(87654321);
        updatedUser.setRentalUnit(1000000020L);
        updatedUser.setAddress("TestvejNy");
        updatedUser.setCity("Rødovre");
        updatedUser.setZipCode(2610);
        updatedUser.setRole(Roles.USER);

        when(userService.updateUser(Mockito.anyLong(), Mockito.any())).thenReturn(updatedUser);

        String userUpdateJson = """
                    {
                        "firstName": "OxUpdated",
                        "lastName": "UpdatedOx123",
                        "email": "Ox@gmail.com",
                        "phoneNumber": 87654321,
                        "address": "TestvejNy",
                        "city": "Rødovre",
                        "zipCode": 2610,
                        "role": "USER"
                        }
                """;

        mockMvc.perform(put("/user/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateJson)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("OxUpdated"))
                .andExpect(jsonPath("$.lastName").value("UpdatedOx123"))
                .andExpect(jsonPath("$.email").value("Ox@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value(87654321))
                .andExpect(jsonPath("$.rentalUnit").value(1000000020L))
                .andExpect(jsonPath("$.address").value("TestvejNy"))
                .andExpect(jsonPath("$.city").value("Rødovre"))
                .andExpect(jsonPath("$.zipCode").value(2610))
                .andExpect(jsonPath("$.role").value("USER"));

    }


    /**
     * Test for updating a user with invalid data.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateUserWithInvalidData() throws Exception {
        String invalidUserUpdateJson = """
                {
                    "firstName": "",
                    "lastName": "UpdatedOx123",
                    "email": "invalidemail",
                    "phoneNumber": 87654321,
                    "address": "TestvejNy",
                    "city": "Rødovre",
                    "zipCode": 2610,
                    "role": "USER"
                }
            """;

        mockMvc.perform(put("/user/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserUpdateJson)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test for deleting a user.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/delete/1")
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(userService).deleteUser(1L);
    }




}