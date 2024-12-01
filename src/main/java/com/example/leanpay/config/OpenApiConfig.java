package com.example.leanpay.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                description = "Loan Calculator API Documentation",
                title = "Loan Calculator API",
                version = "1.0",
                contact = @Contact(
                        name = "Milos Vlku",
                        email = "vlku.milos.dev@gmail.com",
                        url = "https://www.linkedin.com/in/milos-vlku/")),
        servers = {
                @Server(description = "Local ENV", url = "http://localhost:8080")}
)
public class OpenApiConfig {
}
