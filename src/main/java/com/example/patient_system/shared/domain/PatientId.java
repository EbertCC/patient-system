package com.example.patient_system.shared.domain;

import java.util.Objects;

public final class PatientId {

    private final Long value;

    private PatientId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("PatientId debe ser mayor a cero");
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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof PatientId)) return false;
        PatientId patientId = (PatientId) object;
        return Objects.equals(value, patientId.value);
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