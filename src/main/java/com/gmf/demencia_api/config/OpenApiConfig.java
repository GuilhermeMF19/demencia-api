package com.gmf.demencia_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;

@Configuration
public class OpenApiConfig {

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName, createAPIKeyScheme()))
                .info(new Info()
                        .title("RESTful API para monitoramento da demência.")
                        .version("v1")
                        .description("API RESTful para monitoramento da demência, permitindo o cadastro de pacientes, consultas e testes para demência")
                        .termsOfService("https://demência.com.br/teste-api")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://demência.com.br/teste-api")
                        )
                );
    }
}