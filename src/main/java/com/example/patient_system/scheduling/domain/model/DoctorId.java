package com.example.patient_system.scheduling.domain.model;

import java.util.Objects;

public final class DoctorId {
    private final Long value;
    private DoctorId(Long value) {
        if (value == null || value <= 0) throw new IllegalArgumentException("DoctorId debe ser un identificador positivo");
        this.value = value;
    }
    public static DoctorId of(Long value) { return new DoctorId(value); }
    public Long value() { return value; }
    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof DoctorId)) return false; return value.equals(((DoctorId) o).value); }
    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return String.valueOf(value); }
}
