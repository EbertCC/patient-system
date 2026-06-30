package com.example.patient_system.medication.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** DTO de entrada: nunca se expone la entidad de dominio en la frontera. */
public class MedicationRequest {

    @NotNull(message = "patientId es obligatorio")
    private Long patientId;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La dosis es obligatoria")
    private String dosage;

    @NotBlank(message = "La frecuencia es obligatoria")
    private String frequency;

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
}
