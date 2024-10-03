package com.gmf.demencia_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
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
