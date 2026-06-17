package com.example.patient_system.service;

import com.example.patient_system.model.Medication;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class MedicationServiceTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // ─── CASOS INVÁLIDOS (deben fallar la validación) ───────────────────────

    @Test
    void dosage_negativo_conUnidad_debeSerInvalido() {
        // Arrange
        Medication medication = buildMedication("-5mg");

        // Act
        Set<ConstraintViolation<Medication>> violations = validator.validateProperty(medication, "dosage");

        // Assert
        assertFalse(violations.isEmpty(), "Una dosis negativa con unidad (-5mg) debe producir una violación de validación");
    }

    @Test
    void dosage_negativo_sinUnidad_debeSerInvalido() {
        // Arrange
        Medication medication = buildMedication("-100");

        // Act
        Set<ConstraintViolation<Medication>> violations = validator.validateProperty(medication, "dosage");

        // Assert
        assertFalse(violations.isEmpty(), "Una dosis negativa sin unidad (-100) debe producir una violación de validación");
    }

    @Test
    void dosage_vacio_debeSerInvalido() {
        // Arrange
        Medication medication = buildMedication("");

        // Act
        Set<ConstraintViolation<Medication>> violations = validator.validateProperty(medication, "dosage");

        // Assert
        assertFalse(violations.isEmpty(), "Una dosis vacía debe producir una violación de validación");
    }

    // ─── CASOS VÁLIDOS (deben pasar la validación) ──────────────────────────

    @Test
    void dosage_positivo_conMg_debeSerValido() {
        // Arrange
        Medication medication = buildMedication("500mg");

        // Act
        Set<ConstraintViolation<Medication>> violations = validator.validateProperty(medication, "dosage");

        // Assert
        assertTrue(violations.isEmpty(), "Una dosis positiva con unidad mg (500mg) debe ser válida");
    }

    @Test
    void dosage_positivo_conMl_debeSerValido() {
        // Arrange
        Medication medication = buildMedication("10ml");

        // Act
        Set<ConstraintViolation<Medication>> violations = validator.validateProperty(medication, "dosage");

        // Assert
        assertTrue(violations.isEmpty(), "Una dosis positiva con unidad ml (10ml) debe ser válida");
    }

    // ─── Helper ─────────────────────────────────────────────────────────────

    private Medication buildMedication(String dosage) {
        Medication medication = new Medication();
        medication.setName("Paracetamol");
        medication.setDosage(dosage);
        medication.setFrequency("Cada 8 horas");
        return medication;
    }
}