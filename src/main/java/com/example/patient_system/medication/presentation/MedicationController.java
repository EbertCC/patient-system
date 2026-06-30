package com.example.patient_system.medication.presentation;

import com.example.patient_system.medication.application.MedicationService;
import com.example.patient_system.medication.domain.model.Medication;
import com.example.patient_system.medication.presentation.dto.MedicationRequest;
import com.example.patient_system.medication.presentation.dto.MedicationResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/** API REST del modulo Medication. Devuelve/recibe DTOs (JSON), no entidades. */
@RestController
@RequestMapping("/api")
public class MedicationController {

    private final MedicationService service;

    public MedicationController(MedicationService service) {
        this.service = service;
    }

    @PostMapping("/medications")
    public ResponseEntity<MedicationResponse> add(@Valid @RequestBody MedicationRequest request) {
        Medication created = service.addMedication(
                request.getPatientId(),
                request.getName(),
                request.getDosage(),
                request.getFrequency());
        return ResponseEntity.ok(MedicationResponse.from(created));
    }

    @GetMapping("/patients/{patientId}/medications")
    public List<MedicationResponse> byPatient(@PathVariable Long patientId) {
        return service.getMedicationsByPatient(patientId).stream()
                .map(MedicationResponse::from)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/medications/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteMedication(id);
        return ResponseEntity.noContent().build();
    }
}
