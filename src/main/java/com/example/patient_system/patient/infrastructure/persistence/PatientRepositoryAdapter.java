package com.example.patient_system.patient.infrastructure.persistence;

import com.example.patient_system.patient.domain.model.Patient;
import com.example.patient_system.patient.domain.model.PhoneNumber;
import com.example.patient_system.patient.domain.repository.PatientRepository;
import com.example.patient_system.shared.domain.Email;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PatientRepositoryAdapter implements PatientRepository {

    private final SpringDataPatientRepository jpa;

    public PatientRepositoryAdapter(SpringDataPatientRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Patient save(Patient patient) {
        PatientEntity e = new PatientEntity();
        if (patient.id() != null) e.setId(patient.id());
        e.setEmail(patient.email().value());
        e.setName(patient.name());
        e.setPhone(patient.phone().value());
        e.setMedicalHistory(patient.medicalHistory());
        return toDomain(jpa.save(e));
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Patient> findByEmail(Email email) {
        return jpa.findByEmail(email.value()).map(this::toDomain);
    }

    private Patient toDomain(PatientEntity e) {
        Patient p = new Patient(e.getId(), Email.of(e.getEmail()), e.getName(),
                PhoneNumber.of(e.getPhone()), e.getMedicalHistory());
        return p;
    }
}
