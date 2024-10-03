package com.gmf.demencia_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmf.demencia_api.domain.patient.Patient;

public interface PatientRepository extends JpaRepository<Patient, String>{
}
