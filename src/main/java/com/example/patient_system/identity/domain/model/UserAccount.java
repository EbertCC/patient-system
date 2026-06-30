package com.example.patient_system.identity.domain.model;

import com.example.patient_system.shared.domain.Email;

/** Agregado raiz del contexto Identity: la cuenta de acceso (email + contrasena cifrada). */
public class UserAccount {

    private Long id;
    private final Email email;
    private final HashedPassword password;

    public UserAccount(Long id, Email email, HashedPassword password) {
        if (email == null) throw new IllegalArgumentException("El email es obligatorio");
        if (password == null) throw new IllegalArgumentException("La contrasena es obligatoria");
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static UserAccount register(Email email, HashedPassword password) {
        return new UserAccount(null, email, password);
    }

    public void assignId(Long id) { this.id = id; }

    public Long id() { return id; }
    public Email email() { return email; }
    public HashedPassword password() { return password; }
}
