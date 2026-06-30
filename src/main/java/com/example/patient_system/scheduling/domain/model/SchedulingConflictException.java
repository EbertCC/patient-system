package com.example.patient_system.scheduling.domain.model;

/** Se lanza cuando un doctor ya tiene una cita activa en la misma franja. */
public class SchedulingConflictException extends RuntimeException {
    public SchedulingConflictException(String message) {
        super(message);
    }
}
