package com.mycompany.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Palestra {
    private String id;
    private String nome;
    private String descricao;
    private LocalDate data; // Formato ISO 8601 (yyyy-MM-dd)
    private String local;
    private String palestrante;
    private String titulo;
    private int numeroVagas;
    private LocalDate dataLimiteInscricao; // Formato ISO 8601 (yyyy-MM-dd)
    private List<Participante> inscritos;

    // Construtor padrão (obrigatório para deserialização)
    public Palestra() {
        this.inscritos = new ArrayList<>();
    }

    // Construtor com argumentos
    public Palestra(String id, String nome, String descricao, LocalDate data, String local, String palestrante) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.palestrante = palestrante;
        this.inscritos = new ArrayList<>();
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(String palestrante) {
        this.palestrante = palestrante;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumeroVagas() {
        return numeroVagas;
    }

    public void setNumeroVagas(int numeroVagas) {
        if (numeroVagas < 0) {
            throw new IllegalArgumentException("Número de vagas não pode ser negativo.");
        }
        this.numeroVagas = numeroVagas;
    }

    public LocalDate getDataLimiteInscricao() {
        return dataLimiteInscricao;
    }

    public void setDataLimiteInscricao(LocalDate dataLimiteInscricao) {
        if (dataLimiteInscricao != null && data != null && dataLimiteInscricao.isAfter(data)) {
            throw new IllegalArgumentException("Data limite para inscrição não pode ser após a data da palestra.");
        }
        this.dataLimiteInscricao = dataLimiteInscricao;
    }

    public List<Participante> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<Participante> inscritos) {
        this.inscritos = inscritos;
    }

    // Métodos para adicionar e remover inscritos
    public void adicionarInscrito(Participante participante) {
        if (this.inscritos.size() < this.numeroVagas) {
            this.inscritos.add(participante);
        } else {
            throw new IllegalStateException("Número de vagas excedido.");
        }
    }

    public boolean removerInscrito(String cpf) {
        return inscritos.removeIf(p -> p.getCpf().equals(cpf));
    }
}
