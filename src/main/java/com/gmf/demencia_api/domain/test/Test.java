package com.gmf.demencia_api.domain.test;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmf.demencia_api.domain.medicalappointment.MedicalAppointment;
import com.gmf.demencia_api.domain.test.enums.TestType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tests")
public class Test implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TestType type;
    @Column(name="date", nullable = false)
    private String result;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "medical_appointment_id", nullable = false)
    private MedicalAppointment medicalAppointment;
	
    public Test() {
	}

	public Test(TestType type, String result, MedicalAppointment medicalAppointment) {
		this.type = type;
		this.result = result;
		this.medicalAppointment = medicalAppointment;
	}

	public Test(String id, TestType type, String result) {
		this.id = id;
		this.type = type;
		this.result = result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TestType getType() {
		return type;
	}

	public void setType(TestType type) {
		this.type = type;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public MedicalAppointment getMedicalAppointment() {
		return medicalAppointment;
	}

	public void setMedicalAppointment(MedicalAppointment medicalAppointment) {
		this.medicalAppointment = medicalAppointment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, medicalAppointment, result, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		return Objects.equals(id, other.id) && Objects.equals(medicalAppointment, other.medicalAppointment)
				&& Objects.equals(result, other.result) && type == other.type;
	}
}
