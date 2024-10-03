package com.gmf.demencia_api.domain.patient;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.gmf.demencia_api.domain.medicalappointment.MedicalAppointment;
import com.gmf.demencia_api.domain.patient.enums.Gender;
import com.gmf.demencia_api.domain.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="patients")
public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="age", nullable = false)
    private Integer age;
    @Column(name="gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="medical_history", nullable = false)
    private String medicalHistory;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalAppointment> medicalAppointments;
    
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    
	public Patient() {
	}

	public Patient(String name, Integer age, Gender gender, String medicalHistory,
			 User createdBy) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.medicalHistory = medicalHistory;
		this.createdBy = createdBy;
	}

	public Patient(String id, String name, Integer age, Gender gender, String medicalHistory, User createdBy) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.medicalHistory = medicalHistory;
		this.createdBy = createdBy;
	}

	public Patient(String id, String name, Integer age, Gender gender, String medicalHistory,
			List<MedicalAppointment> medicalAppointments, User createdBy) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.medicalHistory = medicalHistory;
		this.medicalAppointments = medicalAppointments;
		this.createdBy = createdBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public List<MedicalAppointment> getMedicalAppointments() {
		return medicalAppointments;
	}

	public void setMedicalAppointments(List<MedicalAppointment> medicalAppointments) {
		this.medicalAppointments = medicalAppointments;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, createdBy, gender, id, medicalAppointments, medicalHistory, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(age, other.age) && Objects.equals(createdBy, other.createdBy) && gender == other.gender
				&& Objects.equals(id, other.id) && Objects.equals(medicalAppointments, other.medicalAppointments)
				&& Objects.equals(medicalHistory, other.medicalHistory) && Objects.equals(name, other.name);
	}
}
