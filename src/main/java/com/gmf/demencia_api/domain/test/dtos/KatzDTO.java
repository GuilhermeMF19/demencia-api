package com.gmf.demencia_api.domain.test.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class KatzDTO {
	
	@NotNull
	@Schema(description = "Id da consulta")
	private String medicalAppointmentId;
	
    @NotNull
    @Schema(description = "Capacidade de tomar banho")
    private int banho;

    @NotNull
    @Schema(description = "Capacidade de se vestir")
    private int vestir;

    @NotNull
    @Schema(description = "Capacidade de usar o banheiro")
    private int usarBanheiro;

    @NotNull
    @Schema(description = "Capacidade de se mover")
    private int mobilidade;

    @NotNull
    @Schema(description = "Controle de continência")
    private int continencia;

    @NotNull
    @Schema(description = "Capacidade de se alimentar")
    private int alimentacao;
    
    // Getters e setters

    public String getMedicalAppointmentId() {
		return medicalAppointmentId;
	}

	public void setMedicalAppointmentId(String medicalAppointmentId) {
		this.medicalAppointmentId = medicalAppointmentId;
	}
	
    public int getBanho() {
        return banho;
    }

	public void setBanho(int banho) {
        this.banho = banho;
    }

    public int getVestir() {
        return vestir;
    }

    public void setVestir(int vestir) {
        this.vestir = vestir;
    }

    public int getUsarBanheiro() {
        return usarBanheiro;
    }

    public void setUsarBanheiro(int usarBanheiro) {
        this.usarBanheiro = usarBanheiro;
    }

    public int getMobilidade() {
        return mobilidade;
    }

    public void setMobilidade(int mobilidade) {
        this.mobilidade = mobilidade;
    }

    public int getContinencia() {
        return continencia;
    }

    public void setContinencia(int continencia) {
        this.continencia = continencia;
    }

    public int getAlimentacao() {
        return alimentacao;
    }

    public void setAlimentacao(int alimentacao) {
        this.alimentacao = alimentacao;
    }
}
