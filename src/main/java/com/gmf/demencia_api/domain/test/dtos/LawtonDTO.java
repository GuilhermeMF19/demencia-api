package com.gmf.demencia_api.domain.test.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class LawtonDTO {

	@NotNull
	@Schema(description = "Id da consulta")
	private String medicalAppointmentId;
	
    @NotNull
    @Schema(description = "Capacidade de usar o telefone")
    private int usarTelefone;

    @NotNull
    @Schema(description = "Capacidade de fazer compras")
    private int fazerCompras;

    @NotNull
    @Schema(description = "Capacidade de preparar refeições")
    private int prepararRefeicoes;

    @NotNull
    @Schema(description = "Capacidade de cuidar da casa")
    private int cuidarDaCasa;

    @NotNull
    @Schema(description = "Capacidade de lavar roupas")
    private int lavarRoupas;

    @NotNull
    @Schema(description = "Capacidade de usar transporte")
    private int usarTransporte;

    @NotNull
    @Schema(description = "Capacidade de administrar medicamentos")
    private int administrarMedicamentos;

    @NotNull
    @Schema(description = "Capacidade de gerenciar finanças")
    private int gerenciarFinancas;
    
    // Getters e Setters

    public String getMedicalAppointmentId() {
		return medicalAppointmentId;
	}

	public void setMedicalAppointmentId(String medicalAppointmentId) {
		this.medicalAppointmentId = medicalAppointmentId;
	}
	
    public int getUsarTelefone() {
        return usarTelefone;
    }

    public void setUsarTelefone(int usarTelefone) {
        this.usarTelefone = usarTelefone;
    }

    public int getFazerCompras() {
        return fazerCompras;
    }

    public void setFazerCompras(int fazerCompras) {
        this.fazerCompras = fazerCompras;
    }

    public int getPrepararRefeicoes() {
        return prepararRefeicoes;
    }

    public void setPrepararRefeicoes(int prepararRefeicoes) {
        this.prepararRefeicoes = prepararRefeicoes;
    }

    public int getCuidarDaCasa() {
        return cuidarDaCasa;
    }

    public void setCuidarDaCasa(int cuidarDaCasa) {
        this.cuidarDaCasa = cuidarDaCasa;
    }

    public int getLavarRoupas() {
        return lavarRoupas;
    }

    public void setLavarRoupas(int lavarRoupas) {
        this.lavarRoupas = lavarRoupas;
    }

    public int getUsarTransporte() {
        return usarTransporte;
    }

    public void setUsarTransporte(int usarTransporte) {
        this.usarTransporte = usarTransporte;
    }

    public int getAdministrarMedicamentos() {
        return administrarMedicamentos;
    }

    public void setAdministrarMedicamentos(int administrarMedicamentos) {
        this.administrarMedicamentos = administrarMedicamentos;
    }

    public int getGerenciarFinancas() {
        return gerenciarFinancas;
    }

    public void setGerenciarFinancas(int gerenciarFinancas) {
        this.gerenciarFinancas = gerenciarFinancas;
    }
}
