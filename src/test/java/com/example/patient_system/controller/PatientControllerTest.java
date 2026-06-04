package com.example.patient_system.controller;

import com.example.patient_system.model.Patient;
import com.example.patient_system.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@DisplayName("PatientController — pruebas de endpoints REST")
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setName("Ana Torres");
        patient.setEmail("ana@example.com");
        patient.setPassword("encoded_password");
        patient.setPhone("987654321");
    }

    @Test
    @DisplayName("POST /register — debe registrar paciente y retornar 200")
    @WithMockUser
    void registerPatient_WithValidData_ShouldReturn200() throws Exception {
        when(patientService.registerPatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/api/patient/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Ana Torres"))
                .andExpect(jsonPath("$.email").value("ana@example.com"));

        verify(patientService, times(1)).registerPatient(any(Patient.class));
    }

    @Test
    @DisplayName("POST /register — debe retornar 400 si el email ya existe")
    @WithMockUser
    void registerPatient_WhenEmailAlreadyExists_ShouldReturn400() throws Exception {
        when(patientService.registerPatient(any(Patient.class)))
                .thenThrow(new IllegalArgumentException("Email already exists"));

        mockMvc.perform(post("/api/patient/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Email already exists"));
    }

    @Test
    @DisplayName("GET /api/patient/{id} — debe retornar el paciente cuando existe")
    @WithMockUser
    void getPatientById_WhenExists_ShouldReturn200() throws Exception {
        when(patientService.getPatientById(1L)).thenReturn(patient);

        mockMvc.perform(get("/api/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Ana Torres"));

        verify(patientService, times(1)).getPatientById(1L);
    }

    @Test
    @DisplayName("GET /api/patient/{id} — debe retornar 404 si el paciente no existe")
    @WithMockUser
    void getPatientById_WhenNotExists_ShouldReturn404() throws Exception {
        when(patientService.getPatientById(99L))
                .thenThrow(new IllegalArgumentException("Patient not found"));

        mockMvc.perform(get("/api/patient/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Patient not found"));
    }

    @Test
    @DisplayName("GET /api/patient/{id} — sin autenticación debe retornar 401")
    void getPatientById_WithoutAuth_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/patient/1"))
                .andExpect(status().isUnauthorized());
    }
}