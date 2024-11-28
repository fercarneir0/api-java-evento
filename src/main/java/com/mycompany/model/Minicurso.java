package com.mycompany.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Minicurso {

    private String id;
    private String nome;
    private LocalDateTime data; // Alterado para LocalDateTime
    private String local;
    private String descricao;
    private String nomeInstrutor;
    private String cpfInstrutor;
    private String emailInstrutor;
    private int numeroVagas;
    private LocalDateTime dataLimiteInscricao; // Alterado para LocalDateTime
    private List<Participante> inscritos;
    private List<Programacao> programacao;

    public Minicurso() {
        this.inscritos = new ArrayList<>();
        this.programacao = new ArrayList<>();
    }

    public Minicurso(String id, String nome, LocalDateTime data, String local, String descricao,
                     String nomeInstrutor, String cpfInstrutor, String emailInstrutor,
                     int numeroVagas, LocalDateTime dataLimiteInscricao) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.descricao = descricao;
        this.nomeInstrutor = nomeInstrutor;
        this.cpfInstrutor = cpfInstrutor;
        this.emailInstrutor = emailInstrutor;
        this.numeroVagas = numeroVagas;
        this.dataLimiteInscricao = dataLimiteInscricao;
        this.inscritos = new ArrayList<>();
        this.programacao = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getNomeInstrutor() {
        return nomeInstrutor;
    }

    public void setNomeInstrutor(String nomeInstrutor) {
        this.nomeInstrutor = nomeInstrutor;
    }

    public String getCpfInstrutor() {
        return cpfInstrutor;
    }

    public void setCpfInstrutor(String cpfInstrutor) {
        this.cpfInstrutor = cpfInstrutor;
    }

    public String getEmailInstrutor() {
        return emailInstrutor;
    }

    public void setEmailInstrutor(String emailInstrutor) {
        this.emailInstrutor = emailInstrutor;
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

    public List<Programacao> getProgramacao() {
        return programacao;
    }

    public void setProgramacao(List<Programacao> programacao) {
        this.programacao = programacao;
    }

    public void adicionarInscrito(Participante participante) {
        if (inscritos.size() < numeroVagas) {
            this.inscritos.add(participante);
        } else {
            throw new IllegalStateException("Número máximo de vagas atingido.");
        }
    }

    public boolean removerInscrito(String cpf) {
        return this.inscritos.removeIf(participante -> participante.getCpf().equals(cpf));
    }

    public void adicionarProgramacao(Programacao programacao) {
        this.programacao.add(programacao);
    }
}
