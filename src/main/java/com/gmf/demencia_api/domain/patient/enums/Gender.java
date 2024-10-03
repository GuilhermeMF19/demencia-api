package com.gmf.demencia_api.domain.patient.enums;

public enum Gender {
	MALE("masculino"),
    FEMALE("feminino");
	
	private String gender;
	
	 Gender(String gender) {
		this.gender = gender;
	}
	 
	public String getGender() {
		return gender;
	}
}
