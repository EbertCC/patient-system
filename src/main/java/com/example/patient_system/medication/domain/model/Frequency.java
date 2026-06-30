package com.example.patient_system.medication.domain.model;

import java.util.Objects;

/** Value object: periodicidad de un medicamento. */
public final class Frequency {

    private final String value;

    private Frequency(String value) {
        this.value = value;
    }

    public static Frequency of(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("La frecuencia es obligatoria");
        }
        return new Frequency(raw.trim());
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frequency)) return false;
        return value.equals(((Frequency) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
