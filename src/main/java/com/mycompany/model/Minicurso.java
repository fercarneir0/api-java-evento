package com.mycompany.model;

import java.util.ArrayList;
import java.util.List;

public class Minicurso {

    private String id;
    private String nome;
    private String descricao;
    private String data;
    private String local;
    private int capacidade;
    private String nomeInstrutor;
    private String cpfInstrutor;
    private String emailInstrutor;
    private int numeroVagas;
    private String dataLimiteInscricao;

    // Relacionamentos
    private List<Programacao> programacao = new ArrayList<>();
    private List<Participante> inscritos = new ArrayList<>();

    // Construtores
    public Minicurso() {}

    public Minicurso(String id, String nome, String descricao, String data, String local, int capacidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.capacidade = capacidade;
        this.numeroVagas = capacidade; // Por padrão, número de vagas é igual à capacidade
    }

    // Getters e Setters
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        if (capacidade < 0) {
            throw new IllegalArgumentException("Capacidade não pode ser negativa.");
        }
        this.capacidade = capacidade;
    }

    public List<Programacao> getProgramacao() {
        return programacao;
    }

    public void setProgramacao(List<Programacao> programacao) {
        this.programacao = programacao;
    }

    public List<Participante> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<Participante> inscritos) {
        this.inscritos = inscritos;
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
        if (numeroVagas < 0 || numeroVagas > this.capacidade) {
            throw new IllegalArgumentException("Número de vagas inválido.");
        }
        this.numeroVagas = numeroVagas;
    }

    public String getDataLimiteInscricao() {
        return dataLimiteInscricao;
    }

    public void setDataLimiteInscricao(String dataLimiteInscricao) {
        if (dataLimiteInscricao == null || dataLimiteInscricao.isEmpty()) {
            throw new IllegalArgumentException("Data limite de inscrição não pode ser vazia.");
        }
        this.dataLimiteInscricao = dataLimiteInscricao;
    }

    // Métodos para manipulação
    public void adicionarProgramacao(Programacao novaProgramacao) {
        this.programacao.add(novaProgramacao);
    }

    public boolean removerInscrito(String cpf) {
        return inscritos.removeIf(participante -> participante.getCpf().equals(cpf));
    }

    public void adicionarInscrito(Participante participante) {
        if (this.inscritos.size() >= this.numeroVagas) {
            throw new IllegalStateException("Número de vagas excedido.");
        }
        this.inscritos.add(participante);
    }
}
