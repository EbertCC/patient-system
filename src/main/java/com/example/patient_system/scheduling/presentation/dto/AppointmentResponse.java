package com.example.patient_system.scheduling.presentation.dto;

import com.example.patient_system.scheduling.domain.model.Appointment;

public class AppointmentResponse {

    private final Long id;
    private final Long patientId;
    private final Long doctorId;
    private final String appointmentTime;
    private final String status;
    private final String notes;

    public AppointmentResponse(Long id, Long patientId, Long doctorId, String appointmentTime, String status, String notes) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.notes = notes;
    }

    public static AppointmentResponse from(Appointment a) {
        return new AppointmentResponse(
                a.id(),
                a.patientId().value(),
                a.doctorId().value(),
                a.timeSlot().time().toString(),
                a.status().name(),
                a.notes());
    }

    public Long getId() { return id; }
    public Long getPatientId() { return patientId; }
    public Long getDoctorId() { return doctorId; }
    public String getAppointmentTime() { return appointmentTime; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
}
