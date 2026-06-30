package com.example.patient_system.patient.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

/** Value object: telefono valido (encapsula el bug corregido del telefono negativo). */
public final class PhoneNumber {

    private static final Pattern PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");

    private final String value;

    private PhoneNumber(String value) {
        this.value = value;
    }

    public static PhoneNumber of(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("El telefono es obligatorio");
        }
        String normalized = raw.trim().replaceAll("[\\s-]", "");
        if (!PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Formato de telefono invalido: " + raw);
        }
        return new PhoneNumber(normalized);
    }

    public String value() { return value; }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof PhoneNumber)) return false; return value.equals(((PhoneNumber) o).value); }
    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return value; }
}
