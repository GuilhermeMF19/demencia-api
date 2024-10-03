package com.gmf.demencia_api.unittest.medicalappointment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.gmf.demencia_api.domain.medicalappointment.MedicalAppointment;
import com.gmf.demencia_api.domain.patient.Patient;
import com.gmf.demencia_api.domain.user.User;
import com.gmf.demencia_api.exceptions.NotFoundException;
import com.gmf.demencia_api.exceptions.UserNotFoundException;
import com.gmf.demencia_api.repositories.MedicalAppointmentRepository;
import com.gmf.demencia_api.repositories.PatientRepository;
import com.gmf.demencia_api.repositories.UserRepository;
import com.gmf.demencia_api.services.MedicalAppointmentService;

@SpringBootTest
public class MedicalAppointmentServiceUnitTest {

    @InjectMocks
    private MedicalAppointmentService medicalAppointmentService;

    @Mock
    private MedicalAppointmentRepository medicalAppointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindAll_ReturnsAllAppointments() {
        // Mocking data
        MedicalAppointment appointment1 = new MedicalAppointment(new Date(), "Checkup", new Patient());
        MedicalAppointment appointment2 = new MedicalAppointment(new Date(), "Follow-up", new Patient());
        
        Page<MedicalAppointment> page = new PageImpl<>(List.of(appointment1, appointment2));
        
        // Mocking repository behavior
        when(medicalAppointmentRepository.findAll((Pageable) any())).thenReturn(page);
        
        // Calling the service method
        Page<MedicalAppointment> result = medicalAppointmentService.findAll(Pageable.ofSize(10));
        
        // Asserting the result
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testFindById_ExistingId_ReturnsAppointment() {
        MedicalAppointment appointment = new MedicalAppointment(new Date(), "Checkup", new Patient());
        when(medicalAppointmentRepository.findById("1")).thenReturn(Optional.of(appointment));

        MedicalAppointment result = medicalAppointmentService.findById("1");

        assertNotNull(result);
        assertEquals("Checkup", result.getComments());
    }

    @Test
    public void testFindById_NonExistingId_ThrowsNotFoundException() {
        when(medicalAppointmentRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            medicalAppointmentService.findById("999");
        });
    }

    @Test
    public void testCreate_ValidAppointment_ReturnsCreatedAppointment() {
        // Mocking dependencies
        User mockUser = new User("user", "encryptedPassword", "User Full Name", null);
        Patient mockPatient = new Patient();
        mockPatient.setId("1");
        
        // Create appointment input
        MedicalAppointment inputAppointment = new MedicalAppointment(new Date(), "Initial appointment", mockPatient);
        
        // Mock behavior
        when(userRepository.findByLogin("user")).thenReturn(mockUser);
        when(patientRepository.findById("1")).thenReturn(Optional.of(mockPatient));
        when(medicalAppointmentRepository.save(any(MedicalAppointment.class))).thenReturn(inputAppointment);
        
        // Create authentication mock
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user");
        
        // Calling the service method
        MedicalAppointment createdAppointment = medicalAppointmentService.create(inputAppointment, authentication);
        
        // Asserting results
        assertNotNull(createdAppointment);
        assertEquals("Initial appointment", createdAppointment.getComments());
    }

    @Test
    public void testCreate_UserNotFound_ThrowsUserNotFoundException() {
        MedicalAppointment appointment = new MedicalAppointment(new Date(), "Checkup", new Patient());
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("nonexistent_user");

        when(userRepository.findByLogin("nonexistent_user")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            medicalAppointmentService.create(appointment, authentication);
        });
    }

    @Test
    public void testUpdate_ExistingId_ReturnsUpdatedAppointment() {
        MedicalAppointment existingAppointment = new MedicalAppointment(new Date(), "Checkup", new Patient());
        when(medicalAppointmentRepository.findById("1")).thenReturn(Optional.of(existingAppointment));

        MedicalAppointment updatedAppointment = new MedicalAppointment(new Date(), "Updated Comments", new Patient());

        when(medicalAppointmentRepository.save(any(MedicalAppointment.class))).thenReturn(updatedAppointment);

        MedicalAppointment result = medicalAppointmentService.update("1", updatedAppointment);

        assertNotNull(result);
        assertEquals("Updated Comments", result.getComments());
    }

    @Test
    public void testUpdate_NonExistingId_ThrowsNotFoundException() {
        when(medicalAppointmentRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            medicalAppointmentService.update("999", new MedicalAppointment());
        });
    }

    @Test
    public void testDeleteById_ExistingId_DeletesAppointment() {
        when(medicalAppointmentRepository.existsById("1")).thenReturn(true);
        
        medicalAppointmentService.deleteById("1");
        
        // Verifying if the delete method was called
        verify(medicalAppointmentRepository).deleteById("1");
    }

    @Test
    public void testDeleteById_NonExistingId_ThrowsNotFoundException() {
        when(medicalAppointmentRepository.existsById("999")).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            medicalAppointmentService.deleteById("999");
        });
    }
}
