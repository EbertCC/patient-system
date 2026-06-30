package com.example.patient_system.patient.presentation.dto;

import com.example.patient_system.patient.domain.model.Patient;
import com.example.patient_system.patient.domain.model.PhoneNumber;
import com.example.patient_system.shared.domain.Email;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/** Verifica que la respuesta NO expone la contrasena (issue #15). */
class PatientResponseTest {

    @Test
    void respuesta_noExponePassword() {
        Patient p = Patient.register(Email.of("ana@mail.com"), "Ana", PhoneNumber.of("987654321"), "");
        p.assignId(1L);

        PatientResponse response = PatientResponse.from(p);

        for (Method m : PatientResponse.class.getMethods()) {
            assertFalse(m.getName().toLowerCase().contains("password"),
                    "PatientResponse no debe tener getter de password: " + m.getName());
        }
        assertEquals("ana@mail.com", response.getEmail());
    }
}
