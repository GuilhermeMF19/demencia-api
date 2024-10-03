package com.gmf.demencia_api.domain.medicalappointment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmf.demencia_api.domain.patient.Patient;
import com.gmf.demencia_api.domain.test.Test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="medicalappointments")
public class MedicalAppointment implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="date", nullable = false)
    private Date date;
    @Column(name="comments", nullable = false, length = 500)
    private String comments;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @OneToMany(mappedBy = "medicalAppointment")
    private List<Test> tests;
    
	public MedicalAppointment() {
	}

	public MedicalAppointment(Date date, String comments, Patient patient) {
		this.date = date;
		this.comments = comments;
		this.patient = patient;
	}
	
	public MedicalAppointment(Date date, String comments, Patient patient, List<Test> tests) {
		this.date = date;
		this.comments = comments;
		this.patient = patient;
		this.tests = tests;
	}

	public MedicalAppointment(String id, Date date, String comments, Patient patient, List<Test> tests) {
		this.id = id;
		this.date = date;
		this.comments = comments;
		this.patient = patient;
		this.tests = tests;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	@Override
	public int hashCode() {
		return Objects.hash(comments, date, id, patient);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalAppointment other = (MedicalAppointment) obj;
		return Objects.equals(comments, other.comments) && Objects.equals(date, other.date)
				&& Objects.equals(id, other.id) && Objects.equals(patient, other.patient);
	}
}
