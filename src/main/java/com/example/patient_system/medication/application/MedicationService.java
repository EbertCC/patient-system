package com.example.patient_system.medication.application;

import com.example.patient_system.medication.domain.model.Dosage;
import com.example.patient_system.medication.domain.model.Frequency;
import com.example.patient_system.medication.domain.model.Medication;
import com.example.patient_system.medication.domain.repository.MedicationRepository;
import com.example.patient_system.shared.domain.PatientId;
import org.springframework.stereotype.Service;

import java.util.List;

/** Servicio de aplicacion: orquesta el caso de uso, depende del puerto (no de JPA). */
@Service
public class MedicationService {

    private final MedicationRepository repository;

    public MedicationService(MedicationRepository repository) {
        this.repository = repository;
    }

    public Medication addMedication(Long patientId, String name, String dosage, String frequency) {
        Medication medication = Medication.create(
                PatientId.of(patientId),
                name,
                Dosage.of(dosage),
                Frequency.of(frequency));
        return repository.save(medication);
    }

    public List<Medication> getMedicationsByPatient(Long patientId) {
        return repository.findByPatientId(PatientId.of(patientId));
    }

    public void deleteMedication(Long medicationId) {
        repository.deleteById(medicationId);
    }
}
