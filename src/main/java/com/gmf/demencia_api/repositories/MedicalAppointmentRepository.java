package com.gmf.demencia_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmf.demencia_api.domain.medicalappointment.MedicalAppointment;

public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointment, String>{
}
