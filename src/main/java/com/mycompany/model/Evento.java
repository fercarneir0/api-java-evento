package com.mycompany.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Evento {

    private String id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String local;
    private String descricao;
    private int numeroVagas;
    private LocalDate dataLimiteInscricao;
    private List<Participante> inscritos = new ArrayList<>();
    private List<Programacao> programacao = new ArrayList<>();
    private String cpfResponsavel;
    private String emailResponsavel;

    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Evento() {}

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

    public String getDataInicio() {
        return dataInicio != null ? dataInicio.format(DATE_FORMATTER) : null;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio != null ? LocalDate.parse(dataInicio, DATE_FORMATTER) : null;
    }

    public String getDataFim() {
        return dataFim != null ? dataFim.format(DATE_FORMATTER) : null;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim != null ? LocalDate.parse(dataFim, DATE_FORMATTER) : null;
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

    public String getDataLimiteInscricao() {
        return dataLimiteInscricao != null ? dataLimiteInscricao.format(DATE_FORMATTER) : null;
    }

    public void setDataLimiteInscricao(String dataLimiteInscricao) {
        this.dataLimiteInscricao = dataLimiteInscricao != null ? LocalDate.parse(dataLimiteInscricao, DATE_FORMATTER) : null;
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

    public String getCpfResponsavel() {
        return cpfResponsavel;
    }

    public void setCpfResponsavel(String cpfResponsavel) {
        this.cpfResponsavel = cpfResponsavel;
    }

    public String getEmailResponsavel() {
        return emailResponsavel;
    }

    public void setEmailResponsavel(String emailResponsavel) {
        this.emailResponsavel = emailResponsavel;
    }

   
    public void adicionarInscrito(Participante participante) {
        if (inscritos.size() >= numeroVagas) {
            throw new IllegalStateException("Número máximo de vagas atingido.");
        }
        inscritos.add(participante);
    }
    
    public boolean removerInscrito(String cpf) {
        return inscritos.removeIf(participante -> participante.getCpf().equals(cpf));
    }

  
    public void adicionarProgramacao(Programacao programacaoItem) {
        programacao.add(programacaoItem);
    }

 
    public void atualizarEvento(Evento eventoAtualizado) {
       
        if (eventoAtualizado.getNome() != null) {
            this.nome = eventoAtualizado.getNome();
        }
        if (eventoAtualizado.getDataInicio() != null) {
            this.dataInicio = eventoAtualizado.dataInicio;
        }
        if (eventoAtualizado.getDataFim() != null) {
            this.dataFim = eventoAtualizado.dataFim;
        }
        if (eventoAtualizado.getLocal() != null) {
            this.local = eventoAtualizado.getLocal();
        }
        if (eventoAtualizado.getDescricao() != null) {
            this.descricao = eventoAtualizado.getDescricao();
        }
        if (eventoAtualizado.getNumeroVagas() > 0) {
            this.numeroVagas = eventoAtualizado.getNumeroVagas();
        }
        if (eventoAtualizado.getDataLimiteInscricao() != null) {
            this.dataLimiteInscricao = eventoAtualizado.dataLimiteInscricao;
        }
        if (eventoAtualizado.getCpfResponsavel() != null) {
            this.cpfResponsavel = eventoAtualizado.getCpfResponsavel();
        }
        if (eventoAtualizado.getEmailResponsavel() != null) {
            this.emailResponsavel = eventoAtualizado.getEmailResponsavel();
        }
       
        if (eventoAtualizado.getProgramacao() != null && !eventoAtualizado.getProgramacao().isEmpty()) {
            this.programacao = eventoAtualizado.getProgramacao();
        }
    }
}
