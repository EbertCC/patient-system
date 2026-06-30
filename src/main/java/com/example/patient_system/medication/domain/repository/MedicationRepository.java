package com.example.patient_system.medication.domain.repository;

import com.example.patient_system.medication.domain.model.Medication;
import com.example.patient_system.shared.domain.PatientId;

import java.util.List;

/** Puerto del dominio. La implementacion (JPA) vive en infrastructure. */
public interface MedicationRepository {

    Medication save(Medication medication);

    List<Medication> findByPatientId(PatientId patientId);

    void deleteById(Long id);
}
