package com.example.patient_system.medication.domain.model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Value object: dosis de un medicamento (cantidad positiva + unidad).
 * Se autovalida al construirse; encapsula la regla que antes vivia como @Pattern.
 */
public final class Dosage {

    private static final Pattern PATTERN =
            Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)\\s?(mg|ml|g|mcg|units?)$");

    private final double amount;
    private final String unit;

    private Dosage(double amount, String unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public static Dosage of(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("La dosis es obligatoria");
        }
        Matcher m = PATTERN.matcher(raw.trim());
        if (!m.matches()) {
            throw new IllegalArgumentException(
                    "La dosis debe ser un numero positivo seguido de unidad (mg, ml, g, mcg, unit): " + raw);
        }
        double parsed = Double.parseDouble(m.group(1));
        if (parsed <= 0) {
            throw new IllegalArgumentException("La dosis debe ser mayor que cero");
        }
        return new Dosage(parsed, m.group(2));
    }

    public double amount() {
        return amount;
    }

    public String unit() {
        return unit;
    }

    /** Representacion normalizada (entero sin decimales). */
    public String value() {
        if (amount == Math.floor(amount)) {
            return ((long) amount) + unit;
        }
        return amount + unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dosage)) return false;
        Dosage dosage = (Dosage) o;
        return Double.compare(dosage.amount, amount) == 0 && unit.equals(dosage.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, unit);
    }

    @Override
    public String toString() {
        return value();
    }
}
