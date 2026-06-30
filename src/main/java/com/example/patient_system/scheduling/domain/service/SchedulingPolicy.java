package com.example.patient_system.scheduling.domain.service;

import com.example.patient_system.scheduling.domain.model.Appointment;
import com.example.patient_system.scheduling.domain.model.SchedulingConflictException;
import com.example.patient_system.scheduling.domain.model.TimeSlot;

import java.util.List;

/** Servicio de dominio: la regla de "no doble reserva" para un doctor. */
public class SchedulingPolicy {

    public void ensureNoConflict(List<Appointment> doctorAppointments, TimeSlot newSlot) {
        boolean conflict = doctorAppointments.stream()
                .filter(a -> a.status().isActive())
                .anyMatch(a -> a.timeSlot().conflictsWith(newSlot));
        if (conflict) {
            throw new SchedulingConflictException("La franja horaria no esta disponible");
        }
    }
}
