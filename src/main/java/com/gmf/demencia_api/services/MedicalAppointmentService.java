package com.gmf.demencia_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmf.demencia_api.domain.medicalappointment.MedicalAppointment;
import com.gmf.demencia_api.domain.patient.Patient;
import com.gmf.demencia_api.domain.user.User;
import com.gmf.demencia_api.exceptions.NotFoundException;
import com.gmf.demencia_api.exceptions.UserNotFoundException;
import com.gmf.demencia_api.repositories.MedicalAppointmentRepository;
import com.gmf.demencia_api.repositories.PatientRepository;
import com.gmf.demencia_api.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class MedicalAppointmentService {

    @Autowired
    private MedicalAppointmentRepository medicalAppointmentRepository;
    
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<MedicalAppointment> findAll(Pageable pageable) {
        return medicalAppointmentRepository.findAll(pageable);
    }

    public MedicalAppointment findById(String id) {
        Optional<MedicalAppointment> obj = medicalAppointmentRepository.findById(id);
        return obj.orElseThrow(() -> new NotFoundException(id));
    }

    @Transactional
    public MedicalAppointment create(MedicalAppointment obj, Authentication authentication) {
        String login = authentication.getName();

        User createdBy = (User) userRepository.findByLogin(login);
        if (createdBy == null) {
            throw new UserNotFoundException(login);
        }
        
        Patient patient = patientRepository.findById(obj.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        MedicalAppointment appointment = new MedicalAppointment(
                obj.getDate(), 
                obj.getComments(), 
                patient
        );
        return medicalAppointmentRepository.save(appointment);
    }

    @Transactional
    public void deleteById(String id) {
        if (!medicalAppointmentRepository.existsById(id)) {
            throw new NotFoundException(id);
        }

        try {
            medicalAppointmentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(id);
        }
    }

    @Transactional
    public MedicalAppointment update(String id, MedicalAppointment obj) {
        Optional<MedicalAppointment> optionalEntity = medicalAppointmentRepository.findById(id);
        MedicalAppointment entity = optionalEntity.orElseThrow(() -> new NotFoundException(id));

        updateData(entity, obj);
        return medicalAppointmentRepository.save(entity);
    }

    private void updateData(MedicalAppointment entity, MedicalAppointment obj) {
        if (obj.getDate() != null) {
            entity.setDate(obj.getDate());
        }
        if (obj.getComments() != null) {
            entity.setComments(null);
        }
        if (obj.getPatient() != null) {
            entity.setPatient(obj.getPatient());
        }
    }
}
