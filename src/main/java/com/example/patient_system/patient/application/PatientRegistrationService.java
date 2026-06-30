package com.example.patient_system.patient.application;

import com.example.patient_system.identity.application.CredentialsService;
import com.example.patient_system.patient.domain.model.Patient;
import com.example.patient_system.patient.domain.model.PhoneNumber;
import com.example.patient_system.patient.domain.repository.PatientRepository;
import com.example.patient_system.shared.domain.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Orquesta el registro entre dos contextos (relacion Customer/Supplier):
 * crea las credenciales en Identity y el perfil en Patient, vinculados por email.
 */
@Service
public class PatientRegistrationService {

    private final CredentialsService credentials;
    private final PatientRepository patients;

    public PatientRegistrationService(CredentialsService credentials, PatientRepository patients) {
        this.credentials = credentials;
        this.patients = patients;
    }

    @Transactional
    public Patient register(String email, String rawPassword, String name, String phone, String medicalHistory) {
        Email validEmail = Email.of(email);
        if (patients.findByEmail(validEmail).isPresent()) {
            throw new IllegalArgumentException("El email ya esta registrado");
        }
        credentials.registerCredentials(validEmail, rawPassword);
        Patient patient = Patient.register(validEmail, name, PhoneNumber.of(phone), medicalHistory);
        return patients.save(patient);
    }
}
