package com.example.patient_system.shared.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void normaliza_aMinusculasYSinEspacios() {
        assertEquals("ana@mail.com", Email.of("  Ana@Mail.COM ").value());
    }

    @Test
    void sinArroba_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> Email.of("sinarroba"));
    }

    @Test
    void vacio_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> Email.of(""));
    }

    @Test
    void dosEmailsConDistintaCaja_sonIguales() {
        assertEquals(Email.of("A@B.com"), Email.of("a@b.com"));
    }
}
