package com.gmf.demencia_api.domain.medicalappointment.dtos;

import java.util.Date;

import com.gmf.demencia_api.domain.medicalappointment.MedicalAppointment;
import com.gmf.demencia_api.domain.patient.Patient;

import jakarta.validation.constraints.NotBlank;

public record MedicalAppointmentDTO(@NotBlank Date date, @NotBlank String comments, @NotBlank Patient patient) {
	public MedicalAppointmentDTO(MedicalAppointment medicalappointment) {
		this(medicalappointment.getDate(), medicalappointment.getComments(), medicalappointment.getPatient());
	}
}
