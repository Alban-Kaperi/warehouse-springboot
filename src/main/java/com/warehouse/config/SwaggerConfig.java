package com.warehouse.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /*
    * - This method defines a Spring bean of type `OpenAPI`.
    * - The returned `OpenAPI` object is part of the OpenAPI v3 specification used by `Swagger` for API documentation.
    * - The method sets up a `security configuration` for the API documentation.
    * - This configuration is implemented to document and enforce a `JWT-based Bearer Token Authentication` strategy across the API.
    */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)  // Important: Token is in the header
                                .name("Authorization"))) // The name of the header

                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")); // Apply globally
    }
}
