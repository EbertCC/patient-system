package com.example.patient_system.scheduling.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDoctorRepository extends JpaRepository<DoctorEntity, Long> {
}
