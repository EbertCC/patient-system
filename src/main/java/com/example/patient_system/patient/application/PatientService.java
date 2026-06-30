package com.example.patient_system.patient.application;

import com.example.patient_system.patient.domain.model.Patient;
import com.example.patient_system.patient.domain.model.PhoneNumber;
import com.example.patient_system.patient.domain.repository.PatientRepository;
import com.example.patient_system.shared.domain.Email;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patients;

    public PatientService(PatientRepository patients) {
        this.patients = patients;
    }

    public Patient getPatientById(Long id) {
        return patients.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
    }

    public Patient findByEmail(String email) {
        return patients.findByEmail(Email.of(email))
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
    }

    public Patient updateProfile(Long id, String name, String phone, String medicalHistory) {
        Patient patient = getPatientById(id);
        patient.updateProfile(name, PhoneNumber.of(phone), medicalHistory);
        return patients.save(patient);
    }
}
