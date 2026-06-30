package com.example.patient_system.identity.domain.service;

import com.example.patient_system.identity.domain.model.HashedPassword;

/** Puerto: el cifrado vive en infrastructure (BCrypt), el dominio solo conoce esta interfaz. */
public interface PasswordHasher {
    HashedPassword hash(String rawPassword);
    boolean matches(String rawPassword, HashedPassword hashed);
}
