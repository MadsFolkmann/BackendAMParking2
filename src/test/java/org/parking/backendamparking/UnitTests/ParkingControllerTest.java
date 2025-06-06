package org.parking.backendamparking.UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.parking.backendamparking.Controller.ParkingController;
import org.parking.backendamparking.DTO.ParkingDTOResponse;
import org.parking.backendamparking.Entity.PArea;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Roles;
import org.parking.backendamparking.Service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(ParkingController.class)
public class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingService parkingService;

    private PArea area1;
    private PArea area2;
    private User user1;
    private User user2;
    private List<ParkingDTOResponse> parkingResponses;

    @BeforeEach
    void setUp() {
        area1 = new PArea();
        area1.setId(1L);
        area1.setAreaName("Rødovre A");
        area1.setCity("Rødovre");
        area1.setPostalCode(2610);
        area1.setDaysAllowedParking(3);

        area2 = new PArea();
        area2.setId(2L);
        area2.setAreaName("Hvidovre B");
        area2.setCity("Hvidovre");
        area2.setPostalCode(2650);
        area2.setDaysAllowedParking(5);

        // test users
        user1 = new User();
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


        user2 = new User();
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

        // Setup parking responses
        ParkingDTOResponse parking1 = new ParkingDTOResponse();
        parking1.setId(1L);
        parking1.setPlateNumber("ABC123");
        parking1.setUserId(1L);
        parking1.setCarColor("Red");
        parking1.setCarBrand("Toyota");
        parking1.setCarModel("Corolla");
        parking1.setStartTime(LocalDateTime.parse("2023-10-01T10:00:00"));
        parking1.setEndTime(LocalDateTime.parse("2023-10-01T12:00:00"));
        parking1.setParea(area1);

        ParkingDTOResponse parking2 = new ParkingDTOResponse();
        parking2.setId(2L);
        parking2.setPlateNumber("XYZ456");
        parking2.setUserId(2L);
        parking2.setCarColor("Blue");
        parking2.setCarBrand("Honda");
        parking2.setCarModel("Civic");
        parking2.setStartTime(LocalDateTime.parse("2023-10-01T11:00:00"));
        parking2.setEndTime(LocalDateTime.parse("2023-10-01T13:00:00"));
        parking2.setParea(area2);

        parkingResponses = Arrays.asList(parking1, parking2);
    }


    /**
     * Test for getting all parkings.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllParkings() throws Exception {
        when(parkingService.getAllParkings()).thenReturn(parkingResponses);

        mockMvc.perform(get("/parking"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].plateNumber", is("ABC123")))
                .andExpect(jsonPath("$[0].carBrand", is("Toyota")))
                .andExpect(jsonPath("$[0].carModel", is("Corolla")))
                .andExpect(jsonPath("$[1].plateNumber", is("XYZ456")))
                .andExpect(jsonPath("$[1].carBrand", is("Honda")))
                .andExpect(jsonPath("$[1].carModel", is("Civic")));
    }

    /**
     * Test for getting a parking by ID.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetParkingById() throws Exception {
        Long parkingId = 1L;
        ParkingDTOResponse parkingResponse = parkingResponses.get(0);
        when(parkingService.getParkingById(parkingId)).thenReturn(parkingResponse);

        mockMvc.perform(get("/parking/" + parkingId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.plateNumber", is("ABC123")))
                .andExpect(jsonPath("$.carBrand", is("Toyota")))
                .andExpect(jsonPath("$.carModel", is("Corolla")))
                .andExpect(jsonPath("$.carColor", is("Red")))
                .andExpect(jsonPath("$.userId", is(1)));
    }

    /**
     * Test for getting active parkings by user ID.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetParkingsByUserId() throws Exception {
        Long userId = 1L;
        List<ParkingDTOResponse> userParkings = Arrays.asList(parkingResponses.get(0));
        when(parkingService.getParkingsByUserId(userId)).thenReturn(userParkings);

        mockMvc.perform(get("/parking/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].plateNumber", is("ABC123")))
                .andExpect(jsonPath("$[0].userId", is(1)));
    }

    /**
     * Test for getting active parkings by user ID and year.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetParkingsByPlateNumber() throws Exception {
        String plateNumber = "ABC123";
        ParkingDTOResponse parkingResponse = parkingResponses.get(0);
        when(parkingService.getParkingByPlateNumber(plateNumber)).thenReturn(parkingResponse);

        mockMvc.perform(get("/parking/plateNumber/" + plateNumber))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.plateNumber", is("ABC123")))
                .andExpect(jsonPath("$.carBrand", is("Toyota")))
                .andExpect(jsonPath("$.carModel", is("Corolla")))
                .andExpect(jsonPath("$.carColor", is("Red")))
                .andExpect(jsonPath("$.userId", is(1)));

    }

    /**
     * Test for checking if a plate number has an active parking.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddParking() throws Exception {
        ParkingDTOResponse newParking = new ParkingDTOResponse();
        newParking.setPlateNumber("ABC123");
        newParking.setUserId(1L);
        newParking.setCarColor("Red");
        newParking.setCarBrand("Toyota");
        newParking.setCarModel("Corolla");
        newParking.setStartTime(LocalDateTime.parse("2023-10-01T10:00:00"));
        newParking.setEndTime(LocalDateTime.parse("2023-10-01T12:00:00"));
        newParking.setParea(area1);

        when(parkingService.addParking(Mockito.any())).thenReturn(newParking);

        String parkingJson = """
                {
                  "plateNumber": "ABC123",
                  "userId": 1,
                  "parea": {
                    "id": 1,
                    "areaName": "Rødovre A",
                    "city": "Rødovre",
                    "postalCode": 2610,
                    "daysAllowedParking": 3
                  },
                  "carColor": "Red",
                  "carBrand": "Toyota",
                  "carModel": "Corolla",
                  "startTime": "2023-10-01T10:00:00",
                  "endTime": "2023-10-01T12:00:00"
                }
                
        """;


        mockMvc.perform(post("/parking/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(parkingJson)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.plateNumber", is("ABC123")))
                .andExpect(jsonPath("$.carBrand", is("Toyota")))
                .andExpect(jsonPath("$.carModel", is("Corolla")))
                .andExpect(jsonPath("$.carColor", is("Red")))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.startTime", is("2023-10-01T10:00:00")))
                .andExpect(jsonPath("$.endTime", is("2023-10-01T12:00:00")))
                .andExpect(jsonPath("$.parea.areaName", is("Rødovre A")));


    }

    /**
     * Test for checking if a plate number has an active parking.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateParking() throws Exception {
        Long parkingId = 1L;
        ParkingDTOResponse updatedParking = new ParkingDTOResponse();
        updatedParking.setId(parkingId);
        updatedParking.setPlateNumber("ABC123");
        updatedParking.setUserId(1L);
        updatedParking.setCarColor("Red");
        updatedParking.setCarBrand("Toyota");
        updatedParking.setCarModel("Corolla");
        updatedParking.setStartTime(LocalDateTime.parse("2023-10-01T10:00:00"));
        updatedParking.setEndTime(LocalDateTime.parse("2023-10-01T12:00:00"));
        updatedParking.setParea(area1);

        when(parkingService.updateParking(Mockito.eq(parkingId), Mockito.any())).thenReturn(updatedParking);

        String parkingJson = """
                {
                  "plateNumber": "ABC123",
                  "userId": 1,
                  "parea": {
                    "id": 1,
                    "areaName": "Rødovre A",
                    "city": "Rødovre",
                    "postalCode": 2610,
                    "daysAllowedParking": 3
                  },
                  "carColor": "Red",
                  "carBrand": "Toyota",
                  "carModel": "Corolla",
                  "startTime": "2023-10-01T10:00:00",
                  "endTime": "2023-10-01T12:00:00"
                }
                
                """;

        mockMvc.perform(put("/parking/" + parkingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(parkingJson)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.plateNumber", is("ABC123")))
                .andExpect(jsonPath("$.carBrand", is("Toyota")))
                .andExpect(jsonPath("$.carModel", is("Corolla")))
                .andExpect(jsonPath("$.carColor", is("Red")))
                .andExpect(jsonPath("$.userId", is(1)));

    }

    /**
     * Test for deleting a parking by ID.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteParking() throws Exception {
        Long parkingId = 1L;

        mockMvc.perform(delete("/parking/" + parkingId)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        Mockito.verify(parkingService).deleteParking(parkingId);

    }

}