package com.example.springDemoWithRest.Payload.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class AuthoritiesDTO {
    @NotBlank 
    @Schema (description = "The authorities for the user", example = "USER",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String authorities;
}
