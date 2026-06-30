package com.example.patient_system.scheduling.domain.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/** Value object: franja horaria de una cita. Dos citas con menos de 30 min de diferencia colisionan. */
public final class TimeSlot {

    private static final Duration BUFFER = Duration.ofMinutes(30);

    private final LocalDateTime time;

    private TimeSlot(LocalDateTime time) {
        if (time == null) throw new IllegalArgumentException("La hora de la cita es obligatoria");
        this.time = time;
    }

    public static TimeSlot at(LocalDateTime time) {
        return new TimeSlot(time);
    }

    public LocalDateTime time() {
        return time;
    }

    public boolean conflictsWith(TimeSlot other) {
        return Duration.between(time, other.time).abs().compareTo(BUFFER) < 0;
    }

    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof TimeSlot)) return false; return time.equals(((TimeSlot) o).time); }
    @Override public int hashCode() { return Objects.hash(time); }
    @Override public String toString() { return time.toString(); }
}
