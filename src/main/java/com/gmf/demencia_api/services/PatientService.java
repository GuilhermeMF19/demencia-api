package com.gmf.demencia_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmf.demencia_api.domain.patient.Patient;
import com.gmf.demencia_api.domain.user.User;
import com.gmf.demencia_api.exceptions.NotFoundException;
import com.gmf.demencia_api.exceptions.UserNotFoundException;
import com.gmf.demencia_api.repositories.PatientRepository;
import com.gmf.demencia_api.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Patient> findAll(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }

    public Patient findById(String id) {
        Optional<Patient> obj = patientRepository.findById(id);
        return obj.orElseThrow(() -> new NotFoundException(id));
    }

    @Transactional
    public Patient create(Patient obj, Authentication authentication) {
        String login = authentication.getName();

        User createdBy = (User) userRepository.findByLogin(login);
        if (createdBy == null) {
            throw new UserNotFoundException(login);
        }

        Patient patient = new Patient(obj.getName(), obj.getAge(), obj.getGender(), obj.getMedicalHistory(), createdBy);
        return patientRepository.save(patient);
    }

    @Transactional
    public void deleteById(String id) {
        if (!patientRepository.existsById(id)) {
            throw new NotFoundException(id);
        }

        try {
            patientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(id);
        }
    }

    @Transactional
    public Patient update(String id, Patient obj) {
        Optional<Patient> optionalEntity = patientRepository.findById(id);
        Patient entity = optionalEntity.orElseThrow(() -> new NotFoundException(id));

        updateData(entity, obj);
        return patientRepository.save(entity);
    }

    private void updateData(Patient entity, Patient obj) {
        if (obj.getName() != null) {
            entity.setName(obj.getName());
        }
        if (obj.getAge() != null) {
            entity.setAge(obj.getAge());
        }
        if (obj.getGender() != null) {
            entity.setGender(null);
        }
        if (obj.getMedicalHistory() != null) {
            entity.setMedicalHistory(obj.getMedicalHistory());
        }
    }
}
