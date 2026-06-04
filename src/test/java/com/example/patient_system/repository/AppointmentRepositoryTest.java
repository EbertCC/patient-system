package com.example.patient_system.repository;

import com.example.patient_system.model.Appointment;
import com.example.patient_system.model.Doctor;
import com.example.patient_system.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de AppointmentRepository.
 *
 * El objetivo principal es verificar la @Query JPQL custom
 * (findByDoctorIdAndAppointmentTimeBetween), que NO se puede
 * comprobar con Mockito porque Mockito no ejecuta SQL real.
 */
@DataJpaTest
@DisplayName("AppointmentRepository — pruebas de queries")
class AppointmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private Patient patient;
    private Doctor doctor;
    private LocalDateTime base; // tiempo base para los tests

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setName("Juan Lopez");
        patient.setEmail("juan@example.com");
        patient.setPassword("pass");
        entityManager.persistAndFlush(patient);

        doctor = new Doctor();
        doctor.setName("Dr. García");
        doctor.setSpecialization("Pediatría");
        entityManager.persistAndFlush(doctor);

        base = LocalDateTime.of(2026, 6, 15, 10, 0);

        // Cita existente en el tiempo base
        Appointment existing = new Appointment();
        existing.setPatient(patient);
        existing.setDoctor(doctor);
        existing.setAppointmentTime(base);
        existing.setStatus("SCHEDULED");
        entityManager.persistAndFlush(existing);
    }

    @Test
    @DisplayName("findByPatientId — debe retornar las citas del paciente")
    void findByPatientId_ShouldReturnPatientAppointments() {
        List<Appointment> result = appointmentRepository.findByPatientId(patient.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPatient().getId()).isEqualTo(patient.getId());
    }

    @Test
    @DisplayName("findByPatientId — debe retornar lista vacía si el paciente no tiene citas")
    void findByPatientId_WhenNoAppointments_ShouldReturnEmpty() {
        // Crear otro paciente sin citas
        Patient other = new Patient();
        other.setName("Sin Citas");
        other.setEmail("sincitas@example.com");
        other.setPassword("pass");
        entityManager.persistAndFlush(other);

        List<Appointment> result = appointmentRepository.findByPatientId(other.getId());

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findByDoctorIdAndAppointmentTimeBetween — debe detectar conflicto en la ventana de 30 min")
    void findByDoctorIdAndAppointmentTimeBetween_WhenConflict_ShouldReturnAppointments() {
        // Buscar citas dentro de ±30 minutos del tiempo base → debe encontrar la cita existente
        List<Appointment> conflicts = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                doctor.getId(),
                base.minusMinutes(30),
                base.plusMinutes(30)
        );

        assertThat(conflicts).hasSize(1);
    }

    @Test
    @DisplayName("findByDoctorIdAndAppointmentTimeBetween — no debe retornar citas fuera de la ventana")
    void findByDoctorIdAndAppointmentTimeBetween_WhenNoConflict_ShouldReturnEmpty() {
        // Buscar 2 horas después — sin solapamiento
        LocalDateTime otherTime = base.plusHours(2);

        List<Appointment> conflicts = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                doctor.getId(),
                otherTime.minusMinutes(30),
                otherTime.plusMinutes(30)
        );

        assertThat(conflicts).isEmpty();
    }
}
