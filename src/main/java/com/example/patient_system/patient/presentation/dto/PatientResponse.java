package com.example.patient_system.patient.presentation.dto;

import com.example.patient_system.patient.domain.model.Patient;

/**
 * DTO de salida. Expone SOLO datos seguros del paciente.
 * No incluye contrasena (issue #15): el password vive en el contexto Identity y nunca se serializa aqui.
 */
public class PatientResponse {

    private final Long id;
    private final String email;
    private final String name;
    private final String phone;
    private final String medicalHistory;

    public PatientResponse(Long id, String email, String name, String phone, String medicalHistory) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.medicalHistory = medicalHistory;
    }

    public static PatientResponse from(Patient p) {
        return new PatientResponse(p.id(), p.email().value(), p.name(), p.phone().value(), p.medicalHistory());
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getMedicalHistory() { return medicalHistory; }
}
