package com.practice.learnspring.controllers;

import com.practice.learnspring.entities.User;
import com.practice.learnspring.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private static final Long USER_ID = 101L;
    private static final String USER_UNAME = "Ankit";
    private static final String USER_DEPT = "Sales";
    private static final String USER_EMAIL = "a.s@gmail.com";
    private static final String URI = "/api/users/";
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    UserController userController;
    @MockBean
    UserService userService;
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void testGetUsers() throws Exception {
        // Setup
        // Configure UserService.findPaginatedUser(...).
        int pageNo=0;
        int pageSize=5;
        User user = getUser();
        final List<User> users = List.of(user);
        Mockito.when(userService.findPaginatedUser(pageNo, pageSize)).thenReturn(users);

        // Run the test
        mockMvc.perform(MockMvcRequestBuilders.get(URI +pageNo+"/"+ pageSize)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].uname").value(users.get(0).getUname()))
                .andReturn().getResponse();
    }

    @Test
    public void testGetUser() throws Exception {
        // Setup
        // Configure UserService.getUser(...).
        final User user = getUser();
        Mockito.when(userService.getUser(USER_ID)).thenReturn(user);

        // Run the test
        mockMvc.perform(MockMvcRequestBuilders.get(URI + USER_ID)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uname").value(user.getUname()))
                .andReturn();
    }

    public User getUser()
    {
        User user = new User();
        user.setId(USER_ID);
        user.setUname(USER_UNAME);
        user.setDepartment(USER_DEPT);
        user.setEmail(USER_EMAIL);
        return user;
    }
}
