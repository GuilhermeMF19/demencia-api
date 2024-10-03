package com.gmf.demencia_api.unittest.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.gmf.demencia_api.domain.patient.Patient;
import com.gmf.demencia_api.domain.patient.enums.Gender;
import com.gmf.demencia_api.domain.user.User;
import com.gmf.demencia_api.exceptions.NotFoundException;
import com.gmf.demencia_api.exceptions.UserNotFoundException;
import com.gmf.demencia_api.repositories.PatientRepository;
import com.gmf.demencia_api.repositories.UserRepository;
import com.gmf.demencia_api.services.PatientService;

@SpringBootTest
@ActiveProfiles("test")
public class PatientServiceUnitTest {

	@InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindAll_ReturnsAllPatients() {
        // Mocking data
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("John Doe", 30, Gender.MALE, "No medical history", null));
        patients.add(new Patient("Jane Doe", 28, Gender.FEMALE, "No medical history", null));

        // Mocking repository behavior
        when(patientRepository.findAll((Pageable) null)).thenReturn(new PageImpl<>(patients));

        // Calling the service method
        Page<Patient> result = patientService.findAll(null);

        // Asserting the result
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testFindById_ExistingId_ReturnsPatient() {
        Patient patient = new Patient("John Doe", 30, Gender.MALE, "No medical history", new User("user", null, null, null, null));
        when(patientRepository.findById("1")).thenReturn(Optional.of(patient));

        Patient result = patientService.findById("1");

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testFindById_NonExistingId_ThrowsNotFoundException() {
        when(patientRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            patientService.findById("999");
        });
    }

    @Test
    public void testCreate_ValidPatient_ReturnsCreatedPatient() {
        // Criar um paciente de entrada (entity)
        Patient inputPatient = new Patient("John Doe", 30, Gender.MALE, "No medical history", null);
        
        // Criar um usuário mock
        User mockUser = new User("user", "encryptedPassword", "User Full Name", null);
        
        // Mockar o retorno do repositório de usuários
        when(userRepository.findByLogin("user")).thenReturn(mockUser);
        
        // Criar um paciente que será retornado após a persistência
        Patient persistedPatient = new Patient("1", "John Doe", 30, Gender.MALE, "No medical history", mockUser);
        
        // Mockar o retorno do repositório ao salvar o paciente
        when(patientRepository.save(any(Patient.class))).thenReturn(persistedPatient);
        
        // Criar um objeto de autenticação mock
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("user");
        
        // Configurar o contexto de segurança
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        
        // Chamar o método create do serviço
        Patient createdPatient = patientService.create(inputPatient, authentication);
        
        // Verificações (asserts)
        assertNotNull(createdPatient); // Verifica se o paciente criado não é nulo
        assertNotNull(createdPatient.getId()); // Verifica se o ID não é nulo
        assertNotNull(createdPatient.getCreatedBy()); // Certifica que o createdBy não é nulo
        assertEquals("John Doe", createdPatient.getName()); // Verifica o nome
        assertEquals(30, createdPatient.getAge()); // Verifica a idade
        assertEquals(Gender.MALE, createdPatient.getGender()); // Verifica o gênero
        assertEquals("user", createdPatient.getCreatedBy().getLogin()); // Verifica se o login está correto
    }

    @Test
    public void testCreate_UserNotFound_ThrowsUserNotFoundException() {
        Patient patient = new Patient("John Doe", 30, Gender.MALE, "No medical history", new User("nonexistent_user", null, null, null, null));
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test_user");

        when(userRepository.findByLogin(authentication.getName())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            patientService.create(patient, authentication);
        });
    }

    @Test
    public void testDeleteById_ExistingId_DeletesPatient() {
        when(patientRepository.existsById("1")).thenReturn(true);

        patientService.deleteById("1");

        // Aqui seria uma boa prática verificar se o método deleteById foi chamado corretamente
    }

    @Test
    public void testDeleteById_NonExistingId_ThrowsNotFoundException() {
        when(patientRepository.existsById("999")).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            patientService.deleteById("999");
        });
    }

    @Test
    public void testUpdate_ExistingId_ReturnsUpdatedPatient() {
        // Criar um paciente existente
        User mockUser = new User("user", "encryptedPassword", "User Full Name", null);
        Patient existingPatient = new Patient("1", "John Doe", 30, Gender.MALE, "No medical history", mockUser);
        
        // Mockar o retorno do repositório ao buscar o paciente
        when(patientRepository.findById("1")).thenReturn(Optional.of(existingPatient));
        
        // Criar um paciente atualizado
        Patient updatedPatient = new Patient("1", "John Updated", 32, Gender.MALE, "Updated medical history", mockUser);
        
        // Mockar o retorno do repositório ao salvar o paciente atualizado
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);
        
        // Chamar o método update do serviço
        Patient result = patientService.update("1", updatedPatient);
        
        // Asserts
        assertNotNull(result); // Verifica se o resultado não é nulo
        assertEquals("John Updated", result.getName()); // Verifica o nome atualizado
        assertEquals(32, result.getAge()); // Verifica a idade atualizada
        assertEquals(Gender.MALE, result.getGender()); // Verifica o gênero
        assertEquals("Updated medical history", result.getMedicalHistory()); // Verifica a história médica atualizada
        assertEquals("user", result.getCreatedBy().getLogin()); // Verifica se o createdBy está correto
    }



    @Test
    public void testUpdate_NonExistingId_ThrowsNotFoundException() {
        when(patientRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            patientService.update("999", new Patient());
        });
    }
}
