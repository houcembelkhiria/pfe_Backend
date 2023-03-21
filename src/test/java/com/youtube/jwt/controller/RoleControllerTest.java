package com.youtube.jwt.controller;

import com.youtube.jwt.entity.Role;
import com.youtube.jwt.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringJUnitConfig
@WebMvcTest(controllers = RoleController.class)
public class RoleControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public RoleService roleService;

    @Test
    public void testCreateNewRole() throws Exception {
        // Create a new Role object with the name "Admin"
        Role role = new Role();
        role.setRoleName("Admin");

        // Mock the behavior of the roleService.createRole() method to return the above Role object
        Mockito.when(roleService.createNewRole(Mockito.any(Role.class))).thenReturn(role);

        // Send a POST request to the "/createNewRole" endpoint with the JSON payload containing the name "Admin"
        mockMvc.perform(MockMvcRequestBuilders.post("/createNewRole")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Admin\"}"))

                // Verify that the response has a status code of 200 OK
                .andExpect(MockMvcResultMatchers.status().isOk())

                // Verify that the response has a JSON property "name" with the value "ROLE_ADMIN"
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ROLE_ADMIN"));
    }

    @Test
    public void testGetAllRoles() throws Exception {
        Role role1 = new Role();
        role1.setRoleName("Admin");
        Role role2 = new Role();
        role2.setRoleName("User");
        List<Role> roles = new ArrayList<>(Arrays.asList(role1, role2));

        Mockito.when(roleService.getAllRoles()).thenReturn(roles);

        mockMvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("User"));
    }
}
