package com.example.patient_system.scheduling.domain.model;

/** Agregado de referencia dentro de Scheduling. */
public class Doctor {

    private final DoctorId id;
    private final String name;
    private final String specialization;
    private final String contact;

    public Doctor(DoctorId id, String name, String specialization, String contact) {
        if (id == null) throw new IllegalArgumentException("DoctorId es obligatorio");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre del doctor es obligatorio");
        this.id = id;
        this.name = name.trim();
        this.specialization = specialization;
        this.contact = contact;
    }

    public DoctorId id() { return id; }
    public String name() { return name; }
    public String specialization() { return specialization; }
    public String contact() { return contact; }
}
