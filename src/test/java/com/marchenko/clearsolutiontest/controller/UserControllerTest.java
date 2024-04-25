package com.marchenko.clearsolutiontest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marchenko.clearsolutiontest.Utils;
import com.marchenko.clearsolutiontest.exceptions.NotFoundException;
import com.marchenko.clearsolutiontest.model.User;
import com.marchenko.clearsolutiontest.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUser_shouldReturn201() throws Exception {
        User user = Utils.getRandomUser();
        Mockito.when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void testCreateUser_shouldReturn400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new User())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserById_shouldReturn200() throws Exception {
        User user = Utils.getRandomUser();
        Mockito.when(userService.getUserById(user.getId())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void testGetUserById_shouldReturn404() throws Exception {
        Mockito.when(userService.getUserById(any(Long.class))).thenThrow(new NotFoundException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUsers_shouldReturn200() throws Exception {
        User user = Utils.getRandomUser();
        List<User> users = List.of(user);
        Mockito.when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(user.getEmail()));
    }

    @Test
    public void testUpdateUserFields_shouldReturn200() throws Exception {
        User user = Utils.getRandomUser();
        Mockito.when(userService.updateSomeFieldsUser(eq(user.getId()), any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/users/{id}", user.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void testUpdateUser_shouldReturn200() throws Exception {
        User user = Utils.getRandomUser();
        Mockito.when(userService.updateUser(eq(user.getId()), any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/users/{id}", user.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void testUpdateUser_shouldReturn400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/users/{id}", 1111111)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new User())))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteUser_shouldReturn204() throws Exception {
        User user = Utils.getRandomUser();
        Mockito.doNothing().when(userService).deleteUserById(user.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/users/{id}", user.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testSearchUsersByBirthDateRange_shouldReturn200() throws Exception {
        User user = Utils.getRandomUser();
        List<User> users = List.of(user);
        Mockito.when(userService.findUsersByBirthDateRange(any(LocalDate.class), any(LocalDate.class))).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users")
                        .param("from", "1999-01-01")
                        .param("to", "2001-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(user.getEmail()));
    }

    @Test
    public void testSearchUsersByBirthDateRange_shouldReturn400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users")
                        .param("from", "2001-01-01")
                        .param("to", "1999-01-01"))
                .andExpect(status().isBadRequest());
    }
}
