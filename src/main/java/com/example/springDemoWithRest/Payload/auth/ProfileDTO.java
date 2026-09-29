package com.example.springDemoWithRest.Payload.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProfileDTO {
    private String email;
    private String authorities;
    private long id;
}
