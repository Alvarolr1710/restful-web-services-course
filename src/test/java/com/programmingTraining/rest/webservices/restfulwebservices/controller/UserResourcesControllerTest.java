package com.programmingTraining.rest.webservices.restfulwebservices.controller;

import com.programmingTraining.rest.webservices.restfulwebservices.RestfulWebServicesApplication;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

import static org.junit.Assert.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RestfulWebServicesApplication.class)
@WebAppConfiguration
public class UserResourcesControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private MvcResult response;
    private String invalidUserId = "123456";

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void shouldRetrieveAllUsers() throws Exception {
        response = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(response.getResponse().getContentAsString(), Is.is(IsNull.notNullValue()));
    }

    @Test
    public void shouldRetrieveUserById() throws Exception {
        response = mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(response.getResponse().getContentAsString().contains("Alvaro"), Is.is(true));
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/")
                .contentType(APPLICATION_JSON)
                .content("{\"name\":\"Alvaro\",\"birthDate\":\"2020-01-14T22:24:12.756+0000\"}"))
                .andExpect(status().isCreated());

    }

    @Test
    public void shouldThrowUserNotFoundException() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/" + invalidUserId))
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getResolvedException()).getLocalizedMessage(),
                Is.is("User with id: \"" + invalidUserId + "\" was not found."));
    }
}