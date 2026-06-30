package com.example.patient_system.patient.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdatePatientRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El telefono es obligatorio")
    private String phone;

    private String medicalHistory;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
}
