package com.programmingTraining.rest.webservices.restfulwebservices.exceptionhandling;

import com.programmingTraining.rest.webservices.restfulwebservices.IntegrationTestBase;
import org.hamcrest.core.Is;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertThat;

public class CustomResponseEntityExceptionHandlerTest extends IntegrationTestBase {


    @Test
    public void shouldThrowUserNotFoundException() {
        String invalidUserId = "12345";
        response = given().get("/users/" + invalidUserId).andReturn();
        assertThat(response.getStatusCode(), Is.is(404));
        assertThat(response.getBody().asString()
                .contains("User with id: '" + invalidUserId + "' was not found."), Is.is(true));
    }

    @Test
    public void shouldThrowPostNotFoundException() {
        String invalidPostId = "12345";
        response = given().get("/users/1/posts/" + invalidPostId).andReturn();
        assertThat(response.getStatusCode(), Is.is(404));
        assertThat(response.getBody().asString()
                .contains("Post with id: '" + invalidPostId + "' was not found."), Is.is(true));
    }


}