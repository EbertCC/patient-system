package com.example.patient_system.patient.domain.repository;

import com.example.patient_system.patient.domain.model.Patient;
import com.example.patient_system.shared.domain.Email;

import java.util.Optional;

public interface PatientRepository {
    Patient save(Patient patient);
    Optional<Patient> findById(Long id);
    Optional<Patient> findByEmail(Email email);
}
