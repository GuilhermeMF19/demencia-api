package com.gmf.demencia_api.domain.patient.dtos;

import com.gmf.demencia_api.domain.patient.Patient;

import jakarta.validation.constraints.NotBlank;

public record PatientIdDTO(@NotBlank String id) {
	public PatientIdDTO(Patient patient) {
		this(patient.getId());
	}
}
