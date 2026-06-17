package com.example.patient_system.repository;

import com.example.patient_system.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@DisplayName("PatientRepository — pruebas de acceso a datos")
class PatientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager; 

    @Autowired
    private PatientRepository patientRepository;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setName("Ana Torres");
        patient.setEmail("ana@example.com");
        patient.setPassword("hashed_password");
        patient.setPhone("987654321");
        entityManager.persistAndFlush(patient);
    }

    @Test
    @DisplayName("findByEmailIgnoreCase — debe encontrar al paciente sin importar mayúsculas")
    void findByEmailIgnoreCase_WhenEmailExists_ShouldReturnPatient() {
        Patient found = patientRepository.findByEmailIgnoreCase("ANA@EXAMPLE.COM");

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualToIgnoringCase("ana@example.com");
        assertThat(found.getName()).isEqualTo("Ana Torres");
    }

    @Test
    @DisplayName("findByEmailIgnoreCase — debe retornar null si el email no existe")
    void findByEmailIgnoreCase_WhenEmailNotExists_ShouldReturnNull() {
        Patient found = patientRepository.findByEmailIgnoreCase("noexiste@example.com");

        assertThat(found).isNull();
    }

    @Test
    @DisplayName("existsByEmailIgnoreCase — debe retornar true si el email ya está registrado")
    void existsByEmailIgnoreCase_WhenEmailExists_ShouldReturnTrue() {
        boolean exists = patientRepository.existsByEmailIgnoreCase("ana@example.com");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("existsByEmailIgnoreCase — debe retornar false si el email no existe")
    void existsByEmailIgnoreCase_WhenEmailNotExists_ShouldReturnFalse() {
        boolean exists = patientRepository.existsByEmailIgnoreCase("nuevo@example.com");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("save — debe persistir un paciente nuevo y asignarle ID")
    void save_NewPatient_ShouldPersistAndReturnWithId() {
        Patient newPatient = new Patient();
        newPatient.setName("Carlos Ruiz");
        newPatient.setEmail("carlos@example.com");
        newPatient.setPassword("hashed_pass");

        Patient saved = patientRepository.save(newPatient);

        assertThat(saved.getId()).isNotNull();
        assertThat(patientRepository.findById(saved.getId())).isPresent();
    }

    @Test
    @DisplayName("findById — debe retornar Optional vacío si el ID no existe")
    void findById_WhenIdNotExists_ShouldReturnEmpty() {
        Optional<Patient> result = patientRepository.findById(999L);

        assertThat(result).isEmpty();
    }
}
