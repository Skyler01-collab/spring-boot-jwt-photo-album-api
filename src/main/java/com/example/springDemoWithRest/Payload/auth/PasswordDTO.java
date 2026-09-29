package com.example.springDemoWithRest.Payload.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter 
@Getter @AllArgsConstructor
@NoArgsConstructor 
public class PasswordDTO {
    @Size(min = 6,max=20, message = "Password must be at least 6 characters long")
    @Schema (description = "The new password for the user", example = "newPassword123",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;

}
