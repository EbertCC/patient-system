package com.example.patient_system.scheduling.presentation.dto;

import com.example.patient_system.scheduling.domain.model.Doctor;

public class DoctorResponse {

    private final Long id;
    private final String name;
    private final String specialization;
    private final String contact;

    public DoctorResponse(Long id, String name, String specialization, String contact) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
    }

    public static DoctorResponse from(Doctor d) {
        return new DoctorResponse(d.id().value(), d.name(), d.specialization(), d.contact());
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getContact() { return contact; }
}
