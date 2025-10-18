package com.ms_reclutador.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name:MS Reclutador}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS Reclutador - Sistema de Gestión de Vacantes")
                        .description("Microservicio para la gestión de vacantes laborales, catálogos y procesos de reclutamiento")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@empresa.com")
                                .url("http://StudentLink.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Servidor de Desarrollo"),
                        new Server()
                                .url("http://api.StudentLink.com")
                                .description("Servidor de Producción")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}