package com.mycompany.model;

import java.sql.Time;
import java.util.Date;

public class InscricaoMinicurso {

    private int minicursoId, userId;
    private Date dataInicio, dataFim, dataInscricao;
    private Time horaInicio, horaFim;
    private String cpfParticipante, emailParticipante, nomeParticipante;

    public InscricaoMinicurso() {
    }

    public InscricaoMinicurso(int miniCursoId, int userId, Date dataInicio, Date dataFim, Date dataInscricao, Time horaInicio, Time horaFim,
            String cpfParticipante, String emailParticipante, String nomeParticipante) {
        this.minicursoId = miniCursoId;
        this.userId = userId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataInscricao = dataInscricao;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.cpfParticipante = cpfParticipante;
        this.emailParticipante = emailParticipante;
        this.nomeParticipante = nomeParticipante;
    }

    public String getCpfParticipante() {
        return cpfParticipante;
    }

    public void setCpfParticipante(String cpfParticipante) {
        this.cpfParticipante = cpfParticipante;
    }

    public String getEmailParticipante() {
        return emailParticipante;
    }

    public void setEmailParticipante(String emailParticipante) {
        this.emailParticipante = emailParticipante;
    }

    public String getNomeParticipante() {
        return nomeParticipante;
    }

    public void setNomeParticipante(String nomeParticipante) {
        this.nomeParticipante = nomeParticipante;
    }

    public int getMinicursoId() {
        return minicursoId;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public void setMinicursoId(int minicursoId) {
        this.minicursoId = minicursoId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Time horaFim) {
        this.horaFim = horaFim;
    }

}
