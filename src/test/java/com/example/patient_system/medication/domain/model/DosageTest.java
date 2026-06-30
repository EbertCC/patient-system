package com.example.patient_system.medication.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Pruebas del value object Dosage (paso "b": casos disenados de valores/accion/resultado). */
class DosageTest {

    // ─── CASOS INVALIDOS ────────────────────────────────────────────────────

    @Test
    void dosage_negativo_conUnidad_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> Dosage.of("-5mg"));
    }

    @Test
    void dosage_negativo_sinUnidad_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> Dosage.of("-100"));
    }

    @Test
    void dosage_vacio_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> Dosage.of(""));
    }

    @Test
    void dosage_cero_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> Dosage.of("0mg"));
    }

    @Test
    void dosage_sinUnidad_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> Dosage.of("500"));
    }

    // ─── CASOS VALIDOS ──────────────────────────────────────────────────────

    @Test
    void dosage_positivo_conMg_esValido() {
        Dosage d = Dosage.of("500mg");
        assertEquals(500.0, d.amount());
        assertEquals("mg", d.unit());
    }

    @Test
    void dosage_positivo_conMl_esValido() {
        assertEquals("10ml", Dosage.of("10ml").value());
    }

    @Test
    void dosage_decimal_esValido() {
        assertEquals(2.5, Dosage.of("2.5mg").amount());
    }

    @Test
    void dos_dosis_conIgualValor_sonIguales() {
        assertEquals(Dosage.of("500mg"), Dosage.of("500mg"));
    }
}
