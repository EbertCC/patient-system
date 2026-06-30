package com.example.patient_system.scheduling.domain.repository;

import com.example.patient_system.scheduling.domain.model.Appointment;
import com.example.patient_system.scheduling.domain.model.DoctorId;
import com.example.patient_system.shared.domain.PatientId;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    List<Appointment> findByPatientId(PatientId patientId);
    List<Appointment> findByDoctorId(DoctorId doctorId);
    Optional<Appointment> findById(Long id);
}
