package com.example.patient_system.patient.domain.model;

import com.example.patient_system.shared.domain.Email;

/** Agregado raiz del contexto Patient: perfil clinico. Ya NO contiene credenciales. */
public class Patient {

    private Long id;
    private final Email email;
    private String name;
    private PhoneNumber phone;
    private String medicalHistory;

    public Patient(Long id, Email email, String name, PhoneNumber phone, String medicalHistory) {
        if (email == null) throw new IllegalArgumentException("El email es obligatorio");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        if (phone == null) throw new IllegalArgumentException("El telefono es obligatorio");
        this.id = id;
        this.email = email;
        this.name = name.trim();
        this.phone = phone;
        this.medicalHistory = medicalHistory == null ? "" : medicalHistory;
    }

    public static Patient register(Email email, String name, PhoneNumber phone, String medicalHistory) {
        return new Patient(null, email, name, phone, medicalHistory);
    }

    public void updateProfile(String name, PhoneNumber phone, String medicalHistory) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        if (phone == null) throw new IllegalArgumentException("El telefono es obligatorio");
        this.name = name.trim();
        this.phone = phone;
        this.medicalHistory = medicalHistory == null ? "" : medicalHistory;
    }

    public void assignId(Long id) { this.id = id; }

    public Long id() { return id; }
    public Email email() { return email; }
    public String name() { return name; }
    public PhoneNumber phone() { return phone; }
    public String medicalHistory() { return medicalHistory; }
}
