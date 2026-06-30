package com.example.patient_system.medication.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataMedicationRepository extends JpaRepository<MedicationEntity, Long> {
    List<MedicationEntity> findByPatientId(Long patientId);
}
