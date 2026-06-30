package com.example.patient_system.patient.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void telefonoConPrefijo_esValido() {
        assertEquals("+51987654321", PhoneNumber.of("+51 987 654 321").value());
    }

    @Test
    void telefonoLocal_esValido() {
        assertEquals("987654321", PhoneNumber.of("987654321").value());
    }

    @Test
    void telefonoNegativo_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumber.of("-123"));
    }

    @Test
    void telefonoConLetras_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumber.of("abc123"));
    }

    @Test
    void telefonoVacio_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumber.of(""));
    }
}
