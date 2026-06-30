package com.example.patient_system.identity.infrastructure.security;

import com.example.patient_system.identity.domain.model.HashedPassword;
import com.example.patient_system.identity.domain.service.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Adaptador: implementa el puerto PasswordHasher con BCrypt. */
@Component
public class BCryptPasswordHasher implements PasswordHasher {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public HashedPassword hash(String rawPassword) {
        return HashedPassword.of(encoder.encode(rawPassword));
    }

    @Override
    public boolean matches(String rawPassword, HashedPassword hashed) {
        return encoder.matches(rawPassword, hashed.value());
    }
}
