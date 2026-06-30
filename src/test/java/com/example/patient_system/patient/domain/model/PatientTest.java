package com.example.patient_system.patient.domain.model;

import com.example.patient_system.shared.domain.Email;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    private Patient newPatient() {
        return Patient.register(Email.of("ana@mail.com"), "Ana Perez", PhoneNumber.of("987654321"), "sin antecedentes");
    }

    @Test
    void registrar_conDatosValidos_creaPaciente() {
        Patient p = newPatient();
        assertEquals("Ana Perez", p.name());
        assertEquals("ana@mail.com", p.email().value());
    }

    @Test
    void registrar_sinNombre_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> Patient.register(Email.of("ana@mail.com"), "  ", PhoneNumber.of("987654321"), ""));
    }

    @Test
    void actualizarPerfil_cambiaNombreYTelefono() {
        Patient p = newPatient();
        p.updateProfile("Ana Gomez", PhoneNumber.of("999888777"), "alergia");
        assertEquals("Ana Gomez", p.name());
        assertEquals("999888777", p.phone().value());
    }
}
