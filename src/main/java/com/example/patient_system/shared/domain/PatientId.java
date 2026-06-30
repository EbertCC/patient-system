package com.example.patient_system.shared.domain;

import java.util.Objects;

/** Value object: identidad de un paciente referenciada entre contextos. */
public final class PatientId {

    private final Long value;

    private PatientId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("PatientId debe ser un identificador positivo");
        }
        this.value = value;
    }

    public static PatientId of(Long value) {
        return new PatientId(value);
    }

    public Long value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientId)) return false;
        return value.equals(((PatientId) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
