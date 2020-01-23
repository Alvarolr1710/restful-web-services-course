package com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandling;

import com.programmingTraining.rest.webservices.restfulwebservices.IntegrationTestBase;
import org.apache.http.HttpStatus;
import org.hamcrest.core.Is;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CustomResponseEntityExceptionHandlerTest extends IntegrationTestBase {


    @Test
    public void shouldThrowUserNotFoundException() {
        String invalidUserId = "12345";
        response = given().get("/users/" + invalidUserId).andReturn();
        assertThat(response.getStatusCode(), is(SC_NOT_FOUND));
        assertThat(response.getBody().asString()
                .contains("User with id: '" + invalidUserId + "' was not found."), is(true));
    }

    @Test
    public void shouldThrowPostNotFoundException() {
        String invalidPostId = "12345";
        response = given().get("/users/1/posts/" + invalidPostId).andReturn();
        assertThat(response.getStatusCode(), is(SC_NOT_FOUND));
        assertThat(response.getBody().asString()
                .contains("Post with id: '" + invalidPostId + "' was not found."), is(true));
    }


}