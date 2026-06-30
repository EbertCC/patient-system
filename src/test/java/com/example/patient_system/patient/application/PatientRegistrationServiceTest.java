package com.example.patient_system.patient.application;

import com.example.patient_system.identity.application.CredentialsService;
import com.example.patient_system.identity.domain.model.HashedPassword;
import com.example.patient_system.identity.domain.model.UserAccount;
import com.example.patient_system.identity.domain.repository.UserAccountRepository;
import com.example.patient_system.identity.domain.service.PasswordHasher;
import com.example.patient_system.patient.domain.model.Patient;
import com.example.patient_system.patient.domain.repository.PatientRepository;
import com.example.patient_system.shared.domain.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PatientRegistrationServiceTest {

    private InMemoryAccounts accounts;
    private InMemoryPatients patients;
    private PatientRegistrationService registration;

    @BeforeEach
    void setUp() {
        accounts = new InMemoryAccounts();
        patients = new InMemoryPatients();
        CredentialsService credentials = new CredentialsService(accounts, new FakeHasher());
        registration = new PatientRegistrationService(credentials, patients);
    }

    @Test
    void register_emailNuevo_creaCuentaYPaciente() {
        Patient p = registration.register("ana@mail.com", "secret1", "Ana", "987654321", "");

        assertNotNull(p.id());
        assertTrue(accounts.findByEmail(Email.of("ana@mail.com")).isPresent(), "debe crear la cuenta");
        assertTrue(patients.findByEmail(Email.of("ana@mail.com")).isPresent(), "debe crear el paciente");
    }

    @Test
    void register_emailDuplicado_lanzaExcepcion() {
        registration.register("ana@mail.com", "secret1", "Ana", "987654321", "");

        assertThrows(IllegalArgumentException.class,
                () -> registration.register("ana@mail.com", "secret2", "Otra", "999888777", ""));
    }

    // ── dobles de prueba ──
    static class FakeHasher implements PasswordHasher {
        public HashedPassword hash(String raw) { return HashedPassword.of("hashed:" + raw); }
        public boolean matches(String raw, HashedPassword hashed) { return hashed.value().equals("hashed:" + raw); }
    }

    static class InMemoryAccounts implements UserAccountRepository {
        private final Map<String, UserAccount> store = new HashMap<>();
        private long seq = 0;
        public UserAccount save(UserAccount a) { if (a.id() == null) a.assignId(++seq); store.put(a.email().value(), a); return a; }
        public Optional<UserAccount> findByEmail(Email email) { return Optional.ofNullable(store.get(email.value())); }
    }

    static class InMemoryPatients implements PatientRepository {
        private final Map<Long, Patient> byId = new HashMap<>();
        private final Map<String, Patient> byEmail = new HashMap<>();
        private long seq = 0;
        public Patient save(Patient p) { if (p.id() == null) p.assignId(++seq); byId.put(p.id(), p); byEmail.put(p.email().value(), p); return p; }
        public Optional<Patient> findById(Long id) { return Optional.ofNullable(byId.get(id)); }
        public Optional<Patient> findByEmail(Email email) { return Optional.ofNullable(byEmail.get(email.value())); }
    }
}
