package com.example.patient_system.scheduling.domain.model;

/** Estado del ciclo de vida de una cita (tipo cerrado: estados validos por construccion). */
public enum AppointmentStatus {
    SCHEDULED,
    COMPLETED,
    CANCELLED;

    public boolean isActive() {
        return this == SCHEDULED;
    }
}
