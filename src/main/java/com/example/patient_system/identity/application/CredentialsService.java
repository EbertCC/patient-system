package com.example.patient_system.identity.application;

import com.example.patient_system.identity.domain.model.HashedPassword;
import com.example.patient_system.identity.domain.model.UserAccount;
import com.example.patient_system.identity.domain.repository.UserAccountRepository;
import com.example.patient_system.identity.domain.service.PasswordHasher;
import com.example.patient_system.shared.domain.Email;
import org.springframework.stereotype.Service;

/** Servicio de aplicacion del contexto Identity. */
@Service
public class CredentialsService {

    private static final int MIN_PASSWORD_LENGTH = 6;

    private final UserAccountRepository accounts;
    private final PasswordHasher passwordHasher;

    public CredentialsService(UserAccountRepository accounts, PasswordHasher passwordHasher) {
        this.accounts = accounts;
        this.passwordHasher = passwordHasher;
    }

    public UserAccount registerCredentials(Email email, String rawPassword) {
        if (rawPassword == null || rawPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("La contrasena debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres");
        }
        if (accounts.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Ya existen credenciales para " + email.value());
        }
        HashedPassword hashed = passwordHasher.hash(rawPassword);
        return accounts.save(UserAccount.register(email, hashed));
    }

    public boolean existsByEmail(Email email) {
        return accounts.findByEmail(email).isPresent();
    }
}
