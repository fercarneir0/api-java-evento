package com.mycompany.model;


import java.util.Date;


public class InscricaoEvento {
    private int eventoId, userId, numeroVagas;
    private String cpfParticipante, nomeParticipante, emailParticipante, telefoneParticipante;
    private Date dataInscricao, dataLimiteInscricao, dataInicio;

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public InscricaoEvento() {
    }
    
    public InscricaoEvento(int eventoId, int userId,int numeroVagas, String cpfParticipante, String nomeParticipante, String emailParticipante, String telefoneParticipante, Date dataInscricao, Date dataLimiteInscricao, Date dataInicio){
        this.eventoId = eventoId;
        this.userId = userId;
        this.numeroVagas = numeroVagas;
        this.cpfParticipante = cpfParticipante;
        this.nomeParticipante = nomeParticipante;
        this.emailParticipante = emailParticipante;
        this.telefoneParticipante = telefoneParticipante;
        this.dataInscricao = dataInscricao;
        this.dataLimiteInscricao = dataLimiteInscricao;
        this.dataInicio = dataInicio;
    }

    public int getNumeroVagas() {
        return numeroVagas;
    }

    public void setNumeroVagas(int numeroVagas) {
        this.numeroVagas = numeroVagas;
    }

    public String getTelefoneParticipante() {
        return telefoneParticipante;
    }

    public void setTelefoneParticipante(String telefoneParticipante) {
        this.telefoneParticipante = telefoneParticipante;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public Date getDataLimiteInscricao() {
        return dataLimiteInscricao;
    }

    public void setDataLimiteInscricao(Date dataLimiteInscricao) {
        this.dataLimiteInscricao = dataLimiteInscricao;
    }
    public int getEventoId() {
        return eventoId;
    }

    public String getCpfParticipante() {
        return cpfParticipante;
    }

    public void setCpfParticipante(String cpfParticipante) {
        this.cpfParticipante = cpfParticipante;
    }

    public String getNomeParticipante() {
        return nomeParticipante;
    }

    public void setNomeParticipante(String nomeParticipante) {
        this.nomeParticipante = nomeParticipante;
    }

    public String getEmailParticipante() {
        return emailParticipante;
    }

    public void setEmailParticipante(String emailParticipante) {
        this.emailParticipante = emailParticipante;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
