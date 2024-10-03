package com.gmf.demencia_api.domain.patient.dtos;

import com.gmf.demencia_api.domain.patient.Patient;
import com.gmf.demencia_api.domain.patient.enums.Gender;

import jakarta.validation.constraints.NotBlank;

public record PatientDTO(@NotBlank String name, @NotBlank Integer age, @NotBlank Gender gender, String medicalHistory) {
	public PatientDTO(Patient patient) {
		this(patient.getName(), patient.getAge(), patient.getGender(), patient.getMedicalHistory());
	}
}
