package com.example.patient_system.medication.presentation.dto;

import com.example.patient_system.medication.domain.model.Medication;

/** DTO de salida. */
public class MedicationResponse {

    private final Long id;
    private final Long patientId;
    private final String name;
    private final String dosage;
    private final String frequency;

    public MedicationResponse(Long id, Long patientId, String name, String dosage, String frequency) {
        this.id = id;
        this.patientId = patientId;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public static MedicationResponse from(Medication m) {
        return new MedicationResponse(
                m.id(),
                m.patientId().value(),
                m.name(),
                m.dosage().value(),
                m.frequency().value());
    }

    public Long getId() { return id; }
    public Long getPatientId() { return patientId; }
    public String getName() { return name; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }
}
