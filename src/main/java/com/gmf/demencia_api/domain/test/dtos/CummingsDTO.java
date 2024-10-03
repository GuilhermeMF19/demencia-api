package com.gmf.demencia_api.domain.test.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class CummingsDTO {

	@NotNull
	@Schema(description = "Id da consulta")
	private String medicalAppointmentId;
	
    @NotNull
    @Schema(description = "Presença de delírios")
    private int delirios;

    @NotNull
    @Schema(description = "Presença de alucinações")
    private int alucinacoes;

    @NotNull
    @Schema(description = "Agitação ou agressão")
    private int agitacaoAgressao;

    @NotNull
    @Schema(description = "Depressão ou disforia")
    private int depressaoDisforia;

    @NotNull
    @Schema(description = "Ansiedade")
    private int ansiedade;

    @NotNull
    @Schema(description = "Euforia")
    private int euforia;

    @NotNull
    @Schema(description = "Apatia ou indiferença")
    private int apatiaIndiferenca;

    @NotNull
    @Schema(description = "Desinibição")
    private int desinibicao;

    @NotNull
    @Schema(description = "Irritabilidade ou labilidade")
    private int irritabilidadeLabilidade;

    @NotNull
    @Schema(description = "Comportamento motor aberrante")
    private int comportamentoMotorAberrante;

    @NotNull
    @Schema(description = "Distúrbios do sono e vigília")
    private int disturbiosSonoVigilia;

    @NotNull
    @Schema(description = "Distúrbios do apetite e alimentação")
    private int disturbiosApetiteAlimentacao;
    
    // Getters e Setters

    public String getMedicalAppointmentId() {
		return medicalAppointmentId;
	}

	public void setMedicalAppointmentId(String medicalAppointmentId) {
		this.medicalAppointmentId = medicalAppointmentId;
	}
	
    public int getDelirios() {
        return delirios;
    }

    public void setDelirios(int delirios) {
        this.delirios = delirios;
    }

    public int getAlucinacoes() {
        return alucinacoes;
    }

    public void setAlucinacoes(int alucinacoes) {
        this.alucinacoes = alucinacoes;
    }

    public int getAgitacaoAgressao() {
        return agitacaoAgressao;
    }

    public void setAgitacaoAgressao(int agitacaoAgressao) {
        this.agitacaoAgressao = agitacaoAgressao;
    }

    public int getDepressaoDisforia() {
        return depressaoDisforia;
    }

    public void setDepressaoDisforia(int depressaoDisforia) {
        this.depressaoDisforia = depressaoDisforia;
    }
    
    public int getAnsiedade() {
        return ansiedade;
    }

    public void setAnsiedade(int ansiedade) {
        this.ansiedade = ansiedade;
    }

    public int getEuforia() {
        return euforia;
    }

    public void setEuforia(int euforia) {
        this.euforia = euforia;
    }

    public int getApatiaIndiferenca() {
        return apatiaIndiferenca;
    }

    public void setApatiaIndiferenca(int apatiaIndiferenca) {
        this.apatiaIndiferenca = apatiaIndiferenca;
    }

    public int getDesinibicao() {
        return desinibicao;
    }

    public void setDesinibicao(int desinibicao) {
        this.desinibicao = desinibicao;
    }

    public int getIrritabilidadeLabilidade() {
        return irritabilidadeLabilidade;
    }

    public void setIrritabilidadeLabilidade(int irritabilidadeLabilidade) {
        this.irritabilidadeLabilidade = irritabilidadeLabilidade;
    }

    public int getComportamentoMotorAberrante() {
        return comportamentoMotorAberrante;
    }

    public void setComportamentoMotorAberrante(int comportamentoMotorAberrante) {
        this.comportamentoMotorAberrante = comportamentoMotorAberrante;
    }

    public int getDisturbiosSonoVigilia() {
        return disturbiosSonoVigilia;
    }

    public void setDisturbiosSonoVigilia(int disturbiosSonoVigilia) {
        this.disturbiosSonoVigilia = disturbiosSonoVigilia;
    }

    public int getDisturbiosApetiteAlimentacao() {
        return disturbiosApetiteAlimentacao;
    }

    public void setDisturbiosApetiteAlimentacao(int disturbiosApetiteAlimentacao) {
        this.disturbiosApetiteAlimentacao = disturbiosApetiteAlimentacao;
    }
}
