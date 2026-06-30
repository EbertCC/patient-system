package com.example.patient_system.scheduling.domain.repository;

import com.example.patient_system.scheduling.domain.model.Doctor;
import com.example.patient_system.scheduling.domain.model.DoctorId;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    List<Doctor> findAll();
    Optional<Doctor> findById(DoctorId doctorId);
}
