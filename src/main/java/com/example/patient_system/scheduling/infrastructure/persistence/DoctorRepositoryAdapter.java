package com.example.patient_system.scheduling.infrastructure.persistence;

import com.example.patient_system.scheduling.domain.model.Doctor;
import com.example.patient_system.scheduling.domain.model.DoctorId;
import com.example.patient_system.scheduling.domain.repository.DoctorRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DoctorRepositoryAdapter implements DoctorRepository {

    private final SpringDataDoctorRepository jpa;

    public DoctorRepositoryAdapter(SpringDataDoctorRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Doctor> findAll() {
        return jpa.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Doctor> findById(DoctorId doctorId) {
        return jpa.findById(doctorId.value()).map(this::toDomain);
    }

    private Doctor toDomain(DoctorEntity e) {
        return new Doctor(DoctorId.of(e.getId()), e.getName(), e.getSpecialization(), e.getContact());
    }
}
