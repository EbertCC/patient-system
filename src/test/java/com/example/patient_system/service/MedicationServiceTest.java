package com.example.patient_system.service;

import com.example.patient_system.model.Medication;
import com.example.patient_system.model.Patient;
import com.example.patient_system.repository.MedicationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationService medicationService;

    private Patient patient;
    private Medication medication;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setName("John Doe");
        patient.setEmail("john@example.com");

        medication = new Medication();
        medication.setId(1L);
        medication.setName("Paracetamol");
        medication.setDosage("500mg");
        medication.setFrequency("Cada 8 horas");
    }

    @Test
    void addMedication_ShouldSaveMedicationWithPatient() {
        when(medicationRepository.save(medication))
                .thenReturn(medication);

        Medication result = medicationService.addMedication(patient, medication);

        assertNotNull(result);
        assertEquals("Paracetamol", result.getName());
        assertEquals("500mg", result.getDosage());
        assertSame(patient, result.getPatient());

        verify(medicationRepository, times(1)).save(medication);
    }

    @Test
    void getMedicationsByPatient_ShouldReturnMedicationList() {
        Medication medication2 = new Medication();
        medication2.setId(2L);
        medication2.setName("Ibuprofeno");
        medication2.setDosage("400mg");
        medication2.setFrequency("Cada 12 horas");
        medication2.setPatient(patient);

        medication.setPatient(patient);

        List<Medication> medications = Arrays.asList(medication, medication2);

        when(medicationRepository.findByPatientId(1L))
                .thenReturn(medications);

        List<?> result = medicationService.getMedicationsByPatient(1L);

        assertEquals(2, result.size());

        verify(medicationRepository, times(1)).findByPatientId(1L);
    }

    @Test
    void deleteMedication_ShouldCallDeleteById() {
        medicationService.deleteMedication(1L);

        verify(medicationRepository, times(1)).deleteById(1L);
    }
}