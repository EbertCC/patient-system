package com.example.patient_system.scheduling.presentation;

import com.example.patient_system.scheduling.application.AppointmentService;
import com.example.patient_system.scheduling.domain.model.Appointment;
import com.example.patient_system.scheduling.presentation.dto.AppointmentRequest;
import com.example.patient_system.scheduling.presentation.dto.AppointmentResponse;
import com.example.patient_system.scheduling.presentation.dto.DoctorResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping("/doctors")
    public List<DoctorResponse> doctors() {
        return service.listDoctors().stream().map(DoctorResponse::from).collect(Collectors.toList());
    }

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> book(@Valid @RequestBody AppointmentRequest request) {
        Appointment appointment = service.bookAppointment(
                request.getPatientId(),
                request.getDoctorId(),
                LocalDateTime.parse(request.getAppointmentTime()),
                request.getNotes());
        return ResponseEntity.ok(AppointmentResponse.from(appointment));
    }

    @GetMapping("/patients/{patientId}/appointments")
    public List<AppointmentResponse> byPatient(@PathVariable Long patientId) {
        return service.getAppointmentsByPatient(patientId).stream()
                .map(AppointmentResponse::from)
                .collect(Collectors.toList());
    }

    @PatchMapping("/appointments/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        service.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
