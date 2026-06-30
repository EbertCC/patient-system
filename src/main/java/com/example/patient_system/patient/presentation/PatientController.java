package com.example.patient_system.patient.presentation;

import com.example.patient_system.patient.application.PatientRegistrationService;
import com.example.patient_system.patient.application.PatientService;
import com.example.patient_system.patient.domain.model.Patient;
import com.example.patient_system.patient.presentation.dto.PatientRegistrationRequest;
import com.example.patient_system.patient.presentation.dto.PatientResponse;
import com.example.patient_system.patient.presentation.dto.UpdatePatientRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientRegistrationService registrationService;

    public PatientController(PatientService patientService, PatientRegistrationService registrationService) {
        this.patientService = patientService;
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<PatientResponse> register(@Valid @RequestBody PatientRegistrationRequest request) {
        Patient patient = registrationService.register(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhone(),
                request.getMedicalHistory());
        return ResponseEntity.ok(PatientResponse.from(patient));
    }

    @GetMapping("/{id}")
    public PatientResponse byId(@PathVariable Long id) {
        return PatientResponse.from(patientService.getPatientById(id));
    }

    @PutMapping("/{id}")
    public PatientResponse update(@PathVariable Long id, @Valid @RequestBody UpdatePatientRequest request) {
        Patient updated = patientService.updateProfile(id, request.getName(), request.getPhone(), request.getMedicalHistory());
        return PatientResponse.from(updated);
    }
}
