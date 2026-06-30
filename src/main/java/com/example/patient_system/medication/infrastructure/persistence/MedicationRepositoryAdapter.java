package com.example.patient_system.medication.infrastructure.persistence;

import com.example.patient_system.medication.domain.model.Dosage;
import com.example.patient_system.medication.domain.model.Frequency;
import com.example.patient_system.medication.domain.model.Medication;
import com.example.patient_system.medication.domain.repository.MedicationRepository;
import com.example.patient_system.shared.domain.PatientId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/** Adaptador: implementa el puerto del dominio usando Spring Data JPA. */
@Repository
public class MedicationRepositoryAdapter implements MedicationRepository {

    private final SpringDataMedicationRepository jpa;

    public MedicationRepositoryAdapter(SpringDataMedicationRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Medication save(Medication medication) {
        MedicationEntity saved = jpa.save(toEntity(medication));
        return toDomain(saved);
    }

    @Override
    public List<Medication> findByPatientId(PatientId patientId) {
        return jpa.findByPatientId(patientId.value()).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    private MedicationEntity toEntity(Medication m) {
        MedicationEntity e = new MedicationEntity();
        if (m.id() != null) {
            e.setId(m.id());
        }
        e.setPatientId(m.patientId().value());
        e.setName(m.name());
        e.setDosage(m.dosage().value());
        e.setFrequency(m.frequency().value());
        return e;
    }

    private Medication toDomain(MedicationEntity e) {
        Medication m = Medication.create(
                PatientId.of(e.getPatientId()),
                e.getName(),
                Dosage.of(e.getDosage()),
                Frequency.of(e.getFrequency()));
        m.assignId(e.getId());
        return m;
    }
}
