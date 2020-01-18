package com.programmingTraining.rest.webservices.restfulwebservices.swagger;

import com.programmingTraining.rest.webservices.restfulwebservices.IntegrationTestBase;
import io.restassured.path.xml.XmlPath;
import org.hamcrest.core.Is;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertThat;

public class SwaggerConfigTest extends IntegrationTestBase {

    @Test
    public void shouldGetSwaggerContracts() throws Exception {
        response = given().request().get("v2/api-docs");
        assertThat(response.getStatusCode(), Is.is(200));
        assertThat(response.getBody().asString().contains("swagger"), Is.is(true));

    }

    @Test
    public void shouldGetSwaggerUi() throws Exception {
        response = given().request().get("swagger-ui.html");
        assertThat(response.getStatusCode(), Is.is(200));
        assertThat(response.getBody().xmlPath(XmlPath.CompatibilityMode.HTML).getString("html.head.title"), Is.is("Swagger UI"));
    }
}