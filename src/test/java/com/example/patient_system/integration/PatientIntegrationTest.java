package com.example.patient_system.integration;

import com.example.patient_system.model.Patient;
import com.example.patient_system.repository.PatientRepository;
import com.example.patient_system.service.PatientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test de integración: verifica que el flujo completo
 * Service → Repository → H2 funciona de extremo a extremo.
 *
 * @SpringBootTest levanta el contexto completo de Spring.
 * La BD usada es H2 (definida en src/test/resources/application.properties).
 *
 * Se limpia la BD antes/después de cada test para garantizar aislamiento.
 */
@SpringBootTest
@ActiveProfiles("test") // Activa el perfil test si tuvieras application-test.properties adicional
@DisplayName("PatientService — pruebas de integración con H2")
class PatientIntegrationTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void cleanUp() {
        patientRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
    }

    @Test
    @DisplayName("registerPatient → findByEmail — flujo completo de registro y búsqueda")
    void registerAndFind_ShouldPersistAndRetrieveCorrectly() {
        Patient input = new Patient();
        input.setName("Laura Quispe");
        input.setEmail("laura@example.com");
        input.setPassword("password123");
        input.setPhone("912345678");

        Patient saved = patientService.registerPatient(input);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getPassword()).doesNotContain("password123"); // Debe estar cifrada

        Patient found = patientService.findByEmail("laura@example.com");
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Laura Quispe");
    }

    @Test
    @DisplayName("registerPatient — no debe permitir registrar el mismo email dos veces")
    void registerPatient_DuplicateEmail_ShouldThrowException() {
        Patient first = new Patient();
        first.setName("Pedro Mamani");
        first.setEmail("pedro@example.com");
        first.setPassword("pass1");
        patientService.registerPatient(first);

        Patient duplicate = new Patient();
        duplicate.setName("Pedro Copia");
        duplicate.setEmail("PEDRO@EXAMPLE.COM"); // mismo email, distinto case
        duplicate.setPassword("pass2");

        assertThatThrownBy(() -> patientService.registerPatient(duplicate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email already exists");
    }

    @Test
    @DisplayName("getPatientById — debe recuperar el paciente persistido en H2")
    void getPatientById_AfterRegister_ShouldReturnCorrectPatient() {
        Patient input = new Patient();
        input.setName("Mario Condori");
        input.setEmail("mario@example.com");
        input.setPassword("pass");
        Patient saved = patientService.registerPatient(input);

        Patient result = patientService.getPatientById(saved.getId());

        assertThat(result.getName()).isEqualTo("Mario Condori");
        assertThat(result.getEmail()).isEqualTo("mario@example.com");
    }
}
