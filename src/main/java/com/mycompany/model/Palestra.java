package com.mycompany.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Palestra {

    private String id;
    private String titulo;
    private String palestrante;
    private LocalDateTime data; 
    private String local;
    private String descricao;
    private int numeroVagas;
    private LocalDateTime dataLimiteInscricao; 
    private List<Participante> inscritos;

    public Palestra() {
        this.inscritos = new ArrayList<>();
    }

    public Palestra(String id, String titulo, String palestrante, LocalDateTime data, String local, String descricao,
                    int numeroVagas, LocalDateTime dataLimiteInscricao) {
        this.id = id;
        this.titulo = titulo;
        this.palestrante = palestrante;
        this.data = data;
        this.local = local;
        this.descricao = descricao;
        this.numeroVagas = numeroVagas;
        this.dataLimiteInscricao = dataLimiteInscricao;
        this.inscritos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(String palestrante) {
        this.palestrante = palestrante;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.data = LocalDateTime.parse(data, formatter);
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNumeroVagas() {
        return numeroVagas;
    }

    public void setNumeroVagas(int numeroVagas) {
        this.numeroVagas = numeroVagas;
    }

    public LocalDateTime getDataLimiteInscricao() {
        return dataLimiteInscricao;
    }

    public void setDataLimiteInscricao(String dataLimiteInscricao) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.dataLimiteInscricao = LocalDateTime.parse(dataLimiteInscricao, formatter);
    }

    public List<Participante> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<Participante> inscritos) {
        this.inscritos = inscritos;
    }

    public void adicionarInscrito(Participante participante) {
        if (inscritos.size() < numeroVagas) {
            this.inscritos.add(participante);
        } else {
            throw new IllegalStateException("Número máximo de vagas atingido.");
        }
    }

    public boolean removerInscrito(String cpf){
        return this.inscritos.removeIf(participante -> participante.getCpf().equals(cpf));
    }
}
