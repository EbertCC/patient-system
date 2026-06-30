package com.example.patient_system.scheduling.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    private final LocalDateTime base = LocalDateTime.of(2026, 7, 1, 10, 0);

    @Test
    void mismaHora_colisiona() {
        assertTrue(TimeSlot.at(base).conflictsWith(TimeSlot.at(base)));
    }

    @Test
    void quinceMinutos_colisiona() {
        assertTrue(TimeSlot.at(base).conflictsWith(TimeSlot.at(base.plusMinutes(15))));
    }

    @Test
    void treintaMinutos_noColisiona() {
        assertFalse(TimeSlot.at(base).conflictsWith(TimeSlot.at(base.plusMinutes(30))));
    }

    @Test
    void cuarentaYCincoMinutos_noColisiona() {
        assertFalse(TimeSlot.at(base).conflictsWith(TimeSlot.at(base.plusMinutes(45))));
    }

    @Test
    void horaNula_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> TimeSlot.at(null));
    }
}
