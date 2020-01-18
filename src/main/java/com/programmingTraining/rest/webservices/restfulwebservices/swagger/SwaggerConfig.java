package com.programmingTraining.rest.webservices.restfulwebservices.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Contact CONTACT_INFO =
            new Contact("Alvaro Rodrigues", "", "AlvaroNumero4@Gmail.com");

    private static final ApiInfo API_DEFAULT_INFO =
            new ApiInfo("MS-Course RESTful Web Service documentation",
                    "This pages documents MS-Course RESTful Web Service endpoints", "1.0",
                    "urs:tos", CONTACT_INFO,
                    "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUME =
            new HashSet<String>(Arrays.asList("application/json", "application/xml"));


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(API_DEFAULT_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUME)
                .consumes(DEFAULT_PRODUCES_AND_CONSUME);
    }

}
