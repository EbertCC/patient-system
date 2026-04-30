package com.example.patient_system.service;

import com.example.patient_system.model.Appointment;
import com.example.patient_system.model.Doctor;
import com.example.patient_system.model.Patient;
import com.example.patient_system.repository.AppointmentRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private AppointmentService appointmentService;

    private Patient patient;
    private Doctor doctor;
    private Appointment appointment;
    private LocalDateTime appointmentTime;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setName("John Doe");
        patient.setEmail("john@example.com");

        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("Dr. Smith");
        doctor.setSpecialization("Cardiología");

        appointmentTime = LocalDateTime.of(2026, 5, 10, 10, 0);

        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(appointmentTime);
    }

    @AfterEach
    void tearDown() {
        patient = null;
        doctor = null;
        appointment = null;
        appointmentTime = null;
    }

    @Test
    void bookAppointment_WhenSlotAvailable_ShouldSaveAppointment() {
        when(doctorService.getDoctorById(1L))
                .thenReturn(doctor);

        when(appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                1L,
                appointmentTime.minusMinutes(30),
                appointmentTime.plusMinutes(30)
        )).thenReturn(Collections.emptyList());

        when(appointmentRepository.save(appointment))
                .thenReturn(appointment);

        Appointment result = appointmentService.bookAppointment(appointment);

        assertNotNull(result);
        assertEquals("SCHEDULED", result.getStatus());
        assertEquals(doctor, result.getDoctor());
        assertEquals(patient, result.getPatient());

        verify(doctorService, times(1)).getDoctorById(1L);
        verify(appointmentRepository, times(1))
                .findByDoctorIdAndAppointmentTimeBetween(
                        1L,
                        appointmentTime.minusMinutes(30),
                        appointmentTime.plusMinutes(30)
                );
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void bookAppointment_WhenSlotUnavailable_ShouldThrowException() {
        Appointment conflictingAppointment = new Appointment();
        conflictingAppointment.setId(2L);
        conflictingAppointment.setDoctor(doctor);
        conflictingAppointment.setPatient(patient);
        conflictingAppointment.setAppointmentTime(appointmentTime);

        when(doctorService.getDoctorById(1L))
                .thenReturn(doctor);

        when(appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                1L,
                appointmentTime.minusMinutes(30),
                appointmentTime.plusMinutes(30)
        )).thenReturn(Arrays.asList(conflictingAppointment));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> appointmentService.bookAppointment(appointment)
        );

        assertEquals("Appointment slot is not available", exception.getMessage());

        verify(doctorService, times(1)).getDoctorById(1L);
        verify(appointmentRepository, times(1))
                .findByDoctorIdAndAppointmentTimeBetween(
                        1L,
                        appointmentTime.minusMinutes(30),
                        appointmentTime.plusMinutes(30)
                );
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void getAppointmentsByPatient_ShouldReturnValidAppointments() {
        Appointment validAppointment = new Appointment();
        validAppointment.setId(1L);
        validAppointment.setPatient(patient);
        validAppointment.setDoctor(doctor);
        validAppointment.setAppointmentTime(appointmentTime);
        validAppointment.setStatus("SCHEDULED");

        Appointment invalidAppointment = new Appointment();
        invalidAppointment.setId(2L);
        invalidAppointment.setPatient(patient);
        invalidAppointment.setDoctor(null);
        invalidAppointment.setAppointmentTime(null);

        when(appointmentRepository.findByPatientId(1L))
                .thenReturn(Arrays.asList(validAppointment, invalidAppointment));

        List result = appointmentService.getAppointmentsByPatient(patient);

        assertEquals(1, result.size());

        verify(appointmentRepository, times(1)).findByPatientId(1L);
    }
}