package com.example.patient_system.medication.domain.model;

import com.example.patient_system.shared.domain.PatientId;

/** Agregado raiz: medicamento recetado a un paciente (referenciado por PatientId). */
public class Medication {

    private Long id;
    private final PatientId patientId;
    private final String name;
    private final Dosage dosage;
    private final Frequency frequency;

    public Medication(Long id, PatientId patientId, String name, Dosage dosage, Frequency frequency) {
        if (patientId == null) {
            throw new IllegalArgumentException("patientId es obligatorio");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del medicamento es obligatorio");
        }
        if (dosage == null) {
            throw new IllegalArgumentException("La dosis es obligatoria");
        }
        if (frequency == null) {
            throw new IllegalArgumentException("La frecuencia es obligatoria");
        }
        this.id = id;
        this.patientId = patientId;
        this.name = name.trim();
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public static Medication create(PatientId patientId, String name, Dosage dosage, Frequency frequency) {
        return new Medication(null, patientId, name, dosage, frequency);
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public Long id() { return id; }
    public PatientId patientId() { return patientId; }
    public String name() { return name; }
    public Dosage dosage() { return dosage; }
    public Frequency frequency() { return frequency; }
}
