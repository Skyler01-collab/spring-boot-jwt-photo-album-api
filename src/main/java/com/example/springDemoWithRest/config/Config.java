package com.example.springDemoWithRest.config;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Demo API",
        version = "Version 1.0",
        contact = @Contact(
            name = "Skyler Homie",
            email = "homieskyler@gmail.com"
        ),
       
       
        description = "Spring Boot Restful API Demo by Skyler"
    )
)
public class Config {
    
}
