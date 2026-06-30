package com.example.patient_system.scheduling.application;

import com.example.patient_system.scheduling.domain.model.*;
import com.example.patient_system.scheduling.domain.repository.AppointmentRepository;
import com.example.patient_system.scheduling.domain.repository.DoctorRepository;
import com.example.patient_system.scheduling.domain.service.SchedulingPolicy;
import com.example.patient_system.shared.domain.PatientId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointments;
    private final DoctorRepository doctors;
    private final SchedulingPolicy schedulingPolicy = new SchedulingPolicy();

    public AppointmentService(AppointmentRepository appointments, DoctorRepository doctors) {
        this.appointments = appointments;
        this.doctors = doctors;
    }

    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime time, String notes) {
        DoctorId did = DoctorId.of(doctorId);
        doctors.findById(did)
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado"));

        TimeSlot slot = TimeSlot.at(time);
        schedulingPolicy.ensureNoConflict(appointments.findByDoctorId(did), slot);

        Appointment appointment = Appointment.schedule(PatientId.of(patientId), did, slot, notes);
        return appointments.save(appointment);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointments.findByPatientId(PatientId.of(patientId));
    }

    public List<Doctor> listDoctors() {
        return doctors.findAll();
    }

    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointments.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
        appointment.cancel();
        appointments.save(appointment);
    }
}
