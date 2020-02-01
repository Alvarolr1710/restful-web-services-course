package com.programmingTraining.rest.webservices.restfulwebservices.controller;

import com.programmingTraining.rest.webservices.restfulwebservices.IntegrationTestBase;
import io.restassured.response.Response;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserResourcesControllerTest extends IntegrationTestBase {

//    @LocalServerPort
//    private int port;
//
//    private Response response;
//
//    @Before
//    public void setUo() {
//        RestAssured.port = port;
//        RestAssured.baseURI = "http://localhost";
//
//    }

    @Test
    public void shouldRetrieveAllUsers() {
        response = given().log().all()
                .when().get("/users").thenReturn();

        assertThat(response.getBody().asString(), is(IsNull.notNullValue()));
        assertThat(response.getStatusCode(), is(SC_OK));
    }

    @Test
    public void shouldRetrieveUserById() {
        given().log().all()
                .when().get("/users/10000")
                .then().log().all().statusCode(SC_OK);
    }

    @Test
    public void shouldCreateNewUser() {
        response = given().body("{\"name\":\"Alvaro\",\"birthDate\":\"2020-01-14T22:24:12.756+0000\"}")
                .contentType("application/json")
                .log().all()
                .when().post("/users/").thenReturn();
        response.then().log().all();

        assertThat(response.statusCode(), is(SC_CREATED));
    }

    @Test
    public void ShouldRetrieveUserPosts() {
        response = given().log().all()
                .when().get("/users/10000/posts");
        response.then().log().all();

        assertThat(response.getBody().asString().contains("10000"), is(true));
        assertThat(response.getBody().asString().contains("20000"), is(true));
        assertThat(response.statusCode(), is(SC_OK));
    }

    @Test
    public void ShouldRetrieveUserSpecificPosts() {
        response = given().log().all()
                .when().get("/users/10000/posts/10000").thenReturn();
        response.then().log().all();

        assertThat(response.getBody().asString().contains("Hola mundo"), is(true));
        assertThat(response.statusCode(), is(SC_OK));
    }

    @Test
    public void shouldCreateNewPostForTheUser() {
        response = given().body("{\"description\":\"this is a test\"}")
                .contentType("application/json")
                .log().all().post("/users/10000/posts/");
        Response result = given().when().get("/users/10000/posts/").thenReturn();
        result.then().log().all();

        assertThat(response.statusCode(), is(SC_CREATED));
        assertThat(result.getBody().asString().contains("this is a test"), is(true));
    }
}