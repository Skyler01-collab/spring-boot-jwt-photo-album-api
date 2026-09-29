package com.example.springDemoWithRest.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rsa")
public record RsaKeyroerties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
    
}
