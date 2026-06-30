package com.example.patient_system.scheduling.domain.service;

import com.example.patient_system.scheduling.domain.model.*;
import com.example.patient_system.shared.domain.PatientId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SchedulingPolicyTest {

    private final SchedulingPolicy policy = new SchedulingPolicy();
    private final LocalDateTime base = LocalDateTime.of(2026, 7, 1, 10, 0);

    private Appointment scheduledAt(LocalDateTime time) {
        return Appointment.schedule(PatientId.of(1L), DoctorId.of(2L), TimeSlot.at(time), "");
    }

    @Test
    void sinCitas_noLanza() {
        assertDoesNotThrow(() -> policy.ensureNoConflict(List.of(), TimeSlot.at(base)));
    }

    @Test
    void citaActivaEnLaMismaFranja_lanzaConflicto() {
        List<Appointment> existing = List.of(scheduledAt(base.plusMinutes(10)));
        assertThrows(SchedulingConflictException.class,
                () -> policy.ensureNoConflict(existing, TimeSlot.at(base)));
    }

    @Test
    void citaCanceladaEnLaMismaFranja_noLanza() {
        Appointment cancelada = scheduledAt(base.plusMinutes(10));
        cancelada.cancel();
        assertDoesNotThrow(() -> policy.ensureNoConflict(List.of(cancelada), TimeSlot.at(base)));
    }

    @Test
    void citaFueraDeLaVentana_noLanza() {
        List<Appointment> existing = List.of(scheduledAt(base.plusMinutes(45)));
        assertDoesNotThrow(() -> policy.ensureNoConflict(existing, TimeSlot.at(base)));
    }
}
