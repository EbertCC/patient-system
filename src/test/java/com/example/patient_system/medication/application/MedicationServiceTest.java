package com.example.patient_system.medication.application;

import com.example.patient_system.medication.domain.model.Medication;
import com.example.patient_system.medication.domain.repository.MedicationRepository;
import com.example.patient_system.shared.domain.PatientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba unitaria del servicio contra un doble del puerto (sin Spring ni BD).
 * Es la ventaja directa de la inversion de dependencias (issue #5).
 */
class MedicationServiceTest {

    private InMemoryMedicationRepository repository;
    private MedicationService service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMedicationRepository();
        service = new MedicationService(repository);
    }

    @Test
    void addMedication_conDatosValidos_persisteYAsignaId() {
        Medication saved = service.addMedication(1L, "Paracetamol", "500mg", "Cada 8 horas");

        assertNotNull(saved.id());
        assertEquals("Paracetamol", saved.name());
        assertEquals("500mg", saved.dosage().value());
    }

    @Test
    void addMedication_conDosisInvalida_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addMedication(1L, "Paracetamol", "-5mg", "Cada 8 horas"));
    }

    @Test
    void getMedicationsByPatient_devuelveSoloLosDelPaciente() {
        service.addMedication(1L, "A", "1mg", "diario");
        service.addMedication(2L, "B", "2mg", "diario");

        assertEquals(1, service.getMedicationsByPatient(1L).size());
    }

    @Test
    void deleteMedication_eliminaDelRepositorio() {
        Medication m = service.addMedication(1L, "A", "1mg", "diario");

        service.deleteMedication(m.id());

        assertTrue(service.getMedicationsByPatient(1L).isEmpty());
    }

    /** Doble de prueba: implementa el puerto del dominio en memoria. */
    static class InMemoryMedicationRepository implements MedicationRepository {
        private final Map<Long, Medication> store = new HashMap<>();
        private long seq = 0;

        @Override
        public Medication save(Medication medication) {
            long id = ++seq;
            medication.assignId(id);
            store.put(id, medication);
            return medication;
        }

        @Override
        public List<Medication> findByPatientId(PatientId patientId) {
            List<Medication> result = new ArrayList<>();
            for (Medication m : store.values()) {
                if (m.patientId().equals(patientId)) {
                    result.add(m);
                }
            }
            return result;
        }

        @Override
        public void deleteById(Long id) {
            store.remove(id);
        }
    }
}
