package com.example.patient_system.scheduling.domain.model;

import com.example.patient_system.shared.domain.PatientId;

/** Agregado raiz: cita entre un paciente y un doctor. Controla sus propias transiciones de estado. */
public class Appointment {

    private Long id;
    private final PatientId patientId;
    private final DoctorId doctorId;
    private final TimeSlot timeSlot;
    private AppointmentStatus status;
    private final String notes;

    public Appointment(Long id, PatientId patientId, DoctorId doctorId, TimeSlot timeSlot,
                       AppointmentStatus status, String notes) {
        if (patientId == null) throw new IllegalArgumentException("patientId es obligatorio");
        if (doctorId == null) throw new IllegalArgumentException("doctorId es obligatorio");
        if (timeSlot == null) throw new IllegalArgumentException("La franja horaria es obligatoria");
        if (status == null) throw new IllegalArgumentException("El estado es obligatorio");
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.timeSlot = timeSlot;
        this.status = status;
        this.notes = notes == null ? "" : notes;
    }

    public static Appointment schedule(PatientId patientId, DoctorId doctorId, TimeSlot timeSlot, String notes) {
        return new Appointment(null, patientId, doctorId, timeSlot, AppointmentStatus.SCHEDULED, notes);
    }

    public void complete() {
        if (status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Solo se puede completar una cita agendada");
        }
        this.status = AppointmentStatus.COMPLETED;
    }

    public void cancel() {
        if (status != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Solo se puede cancelar una cita agendada");
        }
        this.status = AppointmentStatus.CANCELLED;
    }

    public void assignId(Long id) { this.id = id; }

    public Long id() { return id; }
    public PatientId patientId() { return patientId; }
    public DoctorId doctorId() { return doctorId; }
    public TimeSlot timeSlot() { return timeSlot; }
    public AppointmentStatus status() { return status; }
    public String notes() { return notes; }
}
