package org.parking.backendamparking.UnitTests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.parking.backendamparking.Controller.PAreaController;
import org.parking.backendamparking.DTO.PAreaDTOResponse;
import org.parking.backendamparking.Service.PAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PAreaController.class)
public class PAreaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PAreaService pAreaService;

    private List<PAreaDTOResponse> pAreaResponses;

    @BeforeEach
    void setUp() {
        PAreaDTOResponse area1 = new PAreaDTOResponse();
        area1.setId(1L);
        area1.setAreaName("Test Area 1");
        area1.setCity("Test City 1");
        area1.setPostalCode(12345);
        area1.setDaysAllowedParking(7);

        PAreaDTOResponse area2 = new PAreaDTOResponse();
        area2.setId(2L);
        area2.setAreaName("Test Area 2");
        area2.setCity("Test City 2");
        area2.setPostalCode(67890);
        area2.setDaysAllowedParking(5);

        pAreaResponses = List.of(area1, area2);
    }

    /**
     * Test to gell all parking areas.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllPAreas() throws Exception {
        when(pAreaService.getAllPAreas()).thenReturn(pAreaResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/pArea")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].areaName", Matchers.is("Test Area 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].areaName", Matchers.is("Test Area 2")));
    }


    /**
     * Test to get a parking area by area name.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetPAreaByAreaName() throws Exception {
        String areaName = "Test Area 1";
        PAreaDTOResponse areaResponse = pAreaResponses.get(0);
        when(pAreaService.getPAreaByAreaName(areaName)).thenReturn(areaResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/pArea/" + areaName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.areaName", Matchers.is("Test Area 1")));
    }

    /**
     * Test to get a parking area by ID.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetPAreaById() throws Exception {
        Long areaId = 1L;
        PAreaDTOResponse areaResponse = pAreaResponses.get(0);
        when(pAreaService.getPAreaById(areaId)).thenReturn(areaResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/pArea/" + areaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.areaName", Matchers.is("Test Area 1")));
    }

    /**
     * Test to add a new parking area.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAddPArea() throws Exception {
        PAreaDTOResponse newArea = new PAreaDTOResponse();
        newArea.setAreaName("New Area");
        newArea.setCity("New City");
        newArea.setPostalCode(11111);
        newArea.setDaysAllowedParking(3);

when(pAreaService.addPArea(Mockito.any())).thenReturn(newArea);
        String pAreaJson = """
                {
                    "areaName": "New Area",
                    "city": "New City",
                    "postalCode": 11111,
                    "daysAllowedParking": 3
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/pArea/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pAreaJson)
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.areaName", Matchers.is("New Area")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("New City")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postalCode", Matchers.is(11111)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.daysAllowedParking", Matchers.is(3)));
    }

    /**
     * Test to update an existing parking area.
     * @throws Exception
     */


    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdatePArea() throws Exception {
        Long areaId = 1L;
        PAreaDTOResponse updatedArea = new PAreaDTOResponse();
        updatedArea.setId(areaId);
        updatedArea.setAreaName("Updated Area");
        updatedArea.setCity("Updated City");
        updatedArea.setPostalCode(22222);
        updatedArea.setDaysAllowedParking(4);

        when(pAreaService.updatePArea(Mockito.anyLong(), Mockito.any())).thenReturn(updatedArea);

        String pAreaJson = """
                {
                    "areaName": "Updated Area",
                    "city": "Updated City",
                    "postalCode": 22222,
                    "daysAllowedParking": 4
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.put("/pArea/" + areaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(pAreaJson)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.areaName", Matchers.is("Updated Area")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city", Matchers.is("Updated City")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postalCode", Matchers.is(22222)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.daysAllowedParking", Matchers.is(4)));
    }

    /**
     * Test to delete a parking area.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeletePArea() throws Exception {
        Long areaId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/pArea/" + areaId)
                .with(csrf()))
                .andExpect(status().isNoContent());

        Mockito.verify(pAreaService, Mockito.times(1)).deletePArea(areaId);
    }
}
