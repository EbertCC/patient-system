package com.example.patient_system.service;

import com.example.patient_system.model.Patient;
import com.example.patient_system.repository.PatientRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setName("John Doe");
        patient.setEmail("john@example.com");
        patient.setPassword("password");
        patient.setPhone("999999999");
        patient.setMedicalHistory("No tiene antecedentes");
    }

    @Test
    void getPatientById_WhenPatientExists_ShouldReturnPatient() {
        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());

        verify(patientRepository, times(1)).findById(1L);
    }
}