package com.example.patient_system.service;

import com.example.patient_system.model.Patient;
import com.example.patient_system.repository.PatientRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class PatientServiceTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void phone_negativo_debeSerInvalido() {
        // Arrange
        Patient patient = buildPatient("-123456789");

        // Act
        Set<ConstraintViolation<Patient>> violations = validator.validateProperty(patient, "phone");

        // Assert
        assertFalse(violations.isEmpty(), "Un teléfono negativo debe producir una violación de validación");
    }

    @Test
    void phone_soloLetras_debeSerInvalido() {
        // Arrange
        Patient patient = buildPatient("abcdefgh");

        // Act
        Set<ConstraintViolation<Patient>> violations = validator.validateProperty(patient, "phone");

        // Assert
        assertFalse(violations.isEmpty(), "Un teléfono con solo letras debe producir una violación de validación");
    }

    @Test
    void phone_vacio_debeSerInvalido() {
        // Arrange
        Patient patient = buildPatient("");

        // Act
        Set<ConstraintViolation<Patient>> violations = validator.validateProperty(patient, "phone");

        // Assert
        assertFalse(violations.isEmpty(), "Un teléfono vacío debe producir una violación de validación");
    }

    // ─── CASOS VÁLIDOS (deben pasar la validación) ──────────────────────────

    @Test
    void phone_numerosSimples_debeSerValido() {
        // Arrange
        Patient patient = buildPatient("987654321");

        // Act
        Set<ConstraintViolation<Patient>> violations = validator.validateProperty(patient, "phone");

        // Assert
        assertTrue(violations.isEmpty(), "Un teléfono con solo dígitos debe ser válido");
    }

    @Test
    void phone_conCodigoDePais_debeSerValido() {
        // Arrange
        Patient patient = buildPatient("+51987654321");

        // Act
        Set<ConstraintViolation<Patient>> violations = validator.validateProperty(patient, "phone");

        // Assert
        assertTrue(violations.isEmpty(), "Un teléfono con código de país (+51...) debe ser válido");
    }


    private Patient buildPatient(String phone) {
        Patient patient = new Patient();
        patient.setName("Juan Pérez");
        patient.setEmail("juan@example.com");
        patient.setPassword("password123");
        patient.setPhone(phone);
        return patient;
    }
}