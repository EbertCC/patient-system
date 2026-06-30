package com.example.patient_system.scheduling.domain.model;

import com.example.patient_system.shared.domain.PatientId;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    private Appointment newAppointment() {
        return Appointment.schedule(
                PatientId.of(1L),
                DoctorId.of(2L),
                TimeSlot.at(LocalDateTime.of(2026, 7, 1, 10, 0)),
                "control");
    }

    @Test
    void alAgendar_quedaScheduled() {
        assertEquals(AppointmentStatus.SCHEDULED, newAppointment().status());
    }

    @Test
    void completar_desdeScheduled_quedaCompleted() {
        Appointment a = newAppointment();
        a.complete();
        assertEquals(AppointmentStatus.COMPLETED, a.status());
    }

    @Test
    void cancelar_desdeScheduled_quedaCancelled() {
        Appointment a = newAppointment();
        a.cancel();
        assertEquals(AppointmentStatus.CANCELLED, a.status());
    }

    @Test
    void completar_despuesDeCancelar_lanzaExcepcion() {
        Appointment a = newAppointment();
        a.cancel();
        assertThrows(IllegalStateException.class, a::complete);
    }

    @Test
    void cancelar_dosVeces_lanzaExcepcion() {
        Appointment a = newAppointment();
        a.cancel();
        assertThrows(IllegalStateException.class, a::cancel);
    }
}
