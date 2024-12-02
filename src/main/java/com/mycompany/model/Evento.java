package com.mycompany.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Evento {

    private String id; // Identificador único do evento
    private String nome; // Nome do evento
    private LocalDate dataInicio; // Data de início do evento
    private LocalDate dataFim; // Data de término do evento
    private String local; // Local do evento
    private String descricao; // Descrição do evento
    private int numeroVagas; // Número máximo de vagas disponíveis
    private LocalDate dataLimiteInscricao; // Data limite para inscrições
    private List<User> inscritos = new ArrayList<>(); // Lista de usuários inscritos
    private List<Programacao> programacao = new ArrayList<>(); // Lista de programações do evento
    private String cpfResponsavel; // CPF do responsável pelo evento
    private String emailResponsavel; // E-mail do responsável pelo evento

    // Formatação padrão para datas
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Construtor padrão
    public Evento() {}

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
        if (numeroVagas <= 0) {
            throw new IllegalArgumentException("O número de vagas deve ser maior que zero.");
        }
        this.numeroVagas = numeroVagas;
    }

    public String getDataLimiteInscricao() {
        return dataLimiteInscricao != null ? dataLimiteInscricao.format(DATE_FORMATTER) : null;
    }

    public void setDataLimiteInscricao(String dataLimiteInscricao) {
        this.dataLimiteInscricao = dataLimiteInscricao != null ? LocalDate.parse(dataLimiteInscricao, DATE_FORMATTER) : null;
    }

    public List<User> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<User> inscritos) {
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

    // Métodos de manipulação
    public void adicionarInscrito(User user) {
        if (inscritos.size() >= numeroVagas) {
            throw new IllegalStateException("Número máximo de vagas atingido.");
        }
        inscritos.add(user);
    }

    public boolean removerInscrito(String cpf) {
        return inscritos.removeIf(usuario -> usuario.getCpf().equals(cpf));
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

    // Verificação se o evento pode ser atualizado
    public boolean podeSerAtualizado() {
        return LocalDate.now().isBefore(dataInicio);
    }

    // Verificação se o evento pode ser removido
    public boolean podeSerRemovido() {
        return inscritos.isEmpty() && LocalDate.now().isBefore(dataInicio);
    }
}
