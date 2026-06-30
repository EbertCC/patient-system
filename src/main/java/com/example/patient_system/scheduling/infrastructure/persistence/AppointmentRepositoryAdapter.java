package com.example.patient_system.scheduling.infrastructure.persistence;

import com.example.patient_system.scheduling.domain.model.*;
import com.example.patient_system.scheduling.domain.repository.AppointmentRepository;
import com.example.patient_system.shared.domain.PatientId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AppointmentRepositoryAdapter implements AppointmentRepository {

    private final SpringDataAppointmentRepository jpa;

    public AppointmentRepositoryAdapter(SpringDataAppointmentRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Appointment save(Appointment appointment) {
        return toDomain(jpa.save(toEntity(appointment)));
    }

    @Override
    public List<Appointment> findByPatientId(PatientId patientId) {
        return jpa.findByPatientId(patientId.value()).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByDoctorId(DoctorId doctorId) {
        return jpa.findByDoctorId(doctorId.value()).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    private AppointmentEntity toEntity(Appointment a) {
        AppointmentEntity e = new AppointmentEntity();
        if (a.id() != null) e.setId(a.id());
        e.setPatientId(a.patientId().value());
        e.setDoctorId(a.doctorId().value());
        e.setAppointmentTime(a.timeSlot().time());
        e.setStatus(a.status().name());
        e.setNotes(a.notes());
        return e;
    }

    private Appointment toDomain(AppointmentEntity e) {
        Appointment a = new Appointment(
                e.getId(),
                PatientId.of(e.getPatientId()),
                DoctorId.of(e.getDoctorId()),
                TimeSlot.at(e.getAppointmentTime()),
                AppointmentStatus.valueOf(e.getStatus()),
                e.getNotes());
        return a;
    }
}
