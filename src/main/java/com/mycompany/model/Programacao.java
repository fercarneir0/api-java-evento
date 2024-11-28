package com.mycompany.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Programacao {

    private String nome;
    private String descricao;
    private String dataInicio; // Mantido como String para compatibilidade com JSON
    private String dataFim;    // Mantido como String para compatibilidade com JSON
    private String nomePalestrante;
    private String miniCurriculo;

    // Formato de data/hora usado na conversão
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // Construtor padrão
    public Programacao() {}

    // Getters e Setters
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

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getNomePalestrante() {
        return nomePalestrante;
    }

    public void setNomePalestrante(String nomePalestrante) {
        this.nomePalestrante = nomePalestrante;
    }

    public String getMiniCurriculo() {
        return miniCurriculo;
    }

    public void setMiniCurriculo(String miniCurriculo) {
        this.miniCurriculo = miniCurriculo;
    }

    public LocalDateTime getDataInicioAsLocalDateTime() {
        if (dataInicio != null && !dataInicio.isEmpty()) {
            return LocalDateTime.parse(dataInicio, DATE_TIME_FORMATTER);
        }
        return null;
    }

    public LocalDateTime getDataFimAsLocalDateTime() {
        if (dataFim != null && !dataFim.isEmpty()) {
            return LocalDateTime.parse(dataFim, DATE_TIME_FORMATTER);
        }
        return null;
    }
}
