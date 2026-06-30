package com.example.patient_system.identity.domain.model;

import java.util.Objects;

/** Value object: contrasena ya cifrada. Nunca contiene el texto plano. */
public final class HashedPassword {

    private final String hash;

    private HashedPassword(String hash) {
        this.hash = hash;
    }

    public static HashedPassword of(String hash) {
        if (hash == null || hash.isBlank()) {
            throw new IllegalArgumentException("El hash de la contrasena es obligatorio");
        }
        return new HashedPassword(hash);
    }

    public String value() { return hash; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof HashedPassword)) return false; return hash.equals(((HashedPassword) o).hash); }
    @Override public int hashCode() { return Objects.hash(hash); }
    @Override public String toString() { return "HashedPassword{****}"; }
}
