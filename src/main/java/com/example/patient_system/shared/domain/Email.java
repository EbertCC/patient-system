package com.example.patient_system.shared.domain;

import java.util.Objects;
import java.util.regex.Pattern;

/** Value object: identidad de correo compartida entre Identity y Patient. Normaliza a minusculas. */
public final class Email {

    private static final Pattern PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email of(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        String normalized = raw.trim().toLowerCase();
        if (!PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Formato de email invalido: " + raw);
        }
        return new Email(normalized);
    }

    public String value() { return value; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof Email)) return false; return value.equals(((Email) o).value); }
    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return value; }
}
