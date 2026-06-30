package com.example.patient_system.scheduling.application;

import com.example.patient_system.scheduling.domain.model.*;
import com.example.patient_system.scheduling.domain.repository.AppointmentRepository;
import com.example.patient_system.scheduling.domain.repository.DoctorRepository;
import com.example.patient_system.shared.domain.PatientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    private InMemoryAppointmentRepository appointments;
    private InMemoryDoctorRepository doctors;
    private AppointmentService service;
    private final LocalDateTime base = LocalDateTime.of(2026, 7, 1, 10, 0);

    @BeforeEach
    void setUp() {
        appointments = new InMemoryAppointmentRepository();
        doctors = new InMemoryDoctorRepository();
        doctors.add(new Doctor(DoctorId.of(2L), "Dra. House", "Cardiologia", "x@y.com"));
        service = new AppointmentService(appointments, doctors);
    }

    @Test
    void book_conDoctorValido_creaCitaScheduled() {
        Appointment a = service.bookAppointment(1L, 2L, base, "control");
        assertNotNull(a.id());
        assertEquals(AppointmentStatus.SCHEDULED, a.status());
    }

    @Test
    void book_conDoctorInexistente_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> service.bookAppointment(1L, 99L, base, ""));
    }

    @Test
    void book_enHorarioOcupado_lanzaConflicto() {
        service.bookAppointment(1L, 2L, base, "");
        assertThrows(SchedulingConflictException.class,
                () -> service.bookAppointment(1L, 2L, base.plusMinutes(10), ""));
    }

    @Test
    void getAppointmentsByPatient_filtraPorPaciente() {
        service.bookAppointment(1L, 2L, base, "");
        service.bookAppointment(5L, 2L, base.plusHours(2), "");
        assertEquals(1, service.getAppointmentsByPatient(1L).size());
    }

    // ── Dobles de prueba (implementan los puertos) ──
    static class InMemoryAppointmentRepository implements AppointmentRepository {
        private final Map<Long, Appointment> store = new HashMap<>();
        private long seq = 0;
        public Appointment save(Appointment a) {
            if (a.id() == null) a.assignId(++seq);
            store.put(a.id(), a);
            return a;
        }
        public List<Appointment> findByPatientId(PatientId patientId) {
            List<Appointment> r = new ArrayList<>();
            for (Appointment a : store.values()) if (a.patientId().equals(patientId)) r.add(a);
            return r;
        }
        public List<Appointment> findByDoctorId(DoctorId doctorId) {
            List<Appointment> r = new ArrayList<>();
            for (Appointment a : store.values()) if (a.doctorId().equals(doctorId)) r.add(a);
            return r;
        }
        public Optional<Appointment> findById(Long id) { return Optional.ofNullable(store.get(id)); }
    }

    static class InMemoryDoctorRepository implements DoctorRepository {
        private final Map<Long, Doctor> store = new HashMap<>();
        void add(Doctor d) { store.put(d.id().value(), d); }
        public List<Doctor> findAll() { return new ArrayList<>(store.values()); }
        public Optional<Doctor> findById(DoctorId id) { return Optional.ofNullable(store.get(id.value())); }
    }
}
