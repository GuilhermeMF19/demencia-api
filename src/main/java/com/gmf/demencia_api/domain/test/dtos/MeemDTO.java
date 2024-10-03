package com.gmf.demencia_api.domain.test.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class MeemDTO {

    @NotNull
    @Schema(description = "Id da consulta")
    private String medicalAppointmentId;
    
    @NotNull
    @Schema(description = "Pontuação de orientação espacial (0-5 pontos)")
    private int orientacaoEspacial;

    @NotNull
    @Schema(description = "Pontuação de orientação espacial 2 (0-5 pontos)")
    private int orientacaoEspacial2;

    @NotNull
    @Schema(description = "Pontuação de repetição das palavras (0-3 pontos)")
    private int repeticaoPalavras;

    @NotNull
    @Schema(description = "Pontuação de cálculo (0-5 pontos)")
    private int calculo;

    @NotNull
    @Schema(description = "Pontuação de memorização (0-3 pontos)")
    private int memorizacao;

    @NotNull
    @Schema(description = "Pontuação de linguagem (0-2 pontos)")
    private int linguagem1;

    @NotNull
    @Schema(description = "Pontuação de linguagem adicional (1 ponto)")
    private int linguagem2;

    @NotNull
    @Schema(description = "Pontuação de linguagem adicional (0-3 pontos)")
    private int linguagem3;

    @NotNull
    @Schema(description = "Pontuação de linguagem adicional (1 ponto)")
    private int linguagem4;

    @NotNull
    @Schema(description = "Pontuação de linguagem adicional (1 ponto)")
    private int linguagem5;

    @NotNull
    @Schema(description = "Pontuação de linguagem adicional (1 ponto)")
    private int linguagem6;

    // Getters e Setters

    public String getMedicalAppointmentId() {
        return medicalAppointmentId;
    }

    public void setMedicalAppointmentId(String medicalAppointmentId) {
        this.medicalAppointmentId = medicalAppointmentId;
    }

    public int getOrientacaoEspacial() {
        return orientacaoEspacial;
    }

    public void setOrientacaoEspacial(int orientacaoEspacial) {
        this.orientacaoEspacial = orientacaoEspacial;
    }

    public int getOrientacaoEspacial2() {
        return orientacaoEspacial2;
    }

    public void setOrientacaoEspacial2(int orientacaoEspacial2) {
        this.orientacaoEspacial2 = orientacaoEspacial2;
    }

    public int getRepeticaoPalavras() {
        return repeticaoPalavras;
    }

    public void setRepeticaoPalavras(int repeticaoPalavras) {
        this.repeticaoPalavras = repeticaoPalavras;
    }

    public int getCalculo() {
        return calculo;
    }

    public void setCalculo(int calculo) {
        this.calculo = calculo;
    }

    public int getMemorizacao() {
        return memorizacao;
    }

    public void setMemorizacao(int memorizacao) {
        this.memorizacao = memorizacao;
    }

    public int getLinguagem1() {
        return linguagem1;
    }

    public void setLinguagem1(int linguagem1) {
        this.linguagem1 = linguagem1;
    }

    public int getLinguagem2() {
        return linguagem2;
    }

    public void setLinguagem2(int linguagem2) {
        this.linguagem2 = linguagem2;
    }

    public int getLinguagem3() {
        return linguagem3;
    }

    public void setLinguagem3(int linguagem3) {
        this.linguagem3 = linguagem3;
    }

    public int getLinguagem4() {
        return linguagem4;
    }

    public void setLinguagem4(int linguagem4) {
        this.linguagem4 = linguagem4;
    }

    public int getLinguagem5() {
        return linguagem5;
    }

    public void setLinguagem5(int linguagem5) {
        this.linguagem5 = linguagem5;
    }

    public int getLinguagem6() {
        return linguagem6;
    }

    public void setLinguagem6(int linguagem6) {
        this.linguagem6 = linguagem6;
    }
}
