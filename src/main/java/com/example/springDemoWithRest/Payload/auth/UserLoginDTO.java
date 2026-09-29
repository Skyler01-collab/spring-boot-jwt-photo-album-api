package com.example.springDemoWithRest.Payload.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
      @Email
    @Schema(description="Email address", example="admin@example.com",requiredMode=RequiredMode.REQUIRED)
    private String email;
    
    @Size(min = 6, message = "Password must be at least 6 characters long", max=20)
    @Schema(description="Password", example="password123")                                                                              
    private String password;
}
