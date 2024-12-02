package com.mycompany.controller;

import com.mycompany.model.Evento;
import com.mycompany.model.User;
import com.mycompany.model.Programacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoController {

    private static final List<Evento> eventos = new ArrayList<>();

    // Listar todos os eventos
    public List<Evento> listarEventos() {
        return eventos;
    }

    // Criar um novo evento
    public void criarEvento(Evento evento) {
        validarEvento(evento);
        eventos.add(evento);
    }

    // Inscrever um usuário em um evento
    public boolean inscreverUser(String idEvento, User user) {
        Evento evento = buscarEventoPorId(idEvento);
        if (evento != null) {
            try {
                validarInscricao(evento, user);
                evento.adicionarInscrito(user);
                return true;
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Erro ao inscrever usuário: " + e.getMessage());
            }
        }
        throw new IllegalArgumentException("Evento com ID " + idEvento + " não encontrado.");
    }

    // Buscar evento por ID
    public Evento buscarEventoPorId(String idEvento) {
        return eventos.stream()
                .filter(evento -> evento.getId().equals(idEvento))
                .findFirst()
                .orElse(null);
    }

    // Remover um usuário de um evento
    public boolean removerUser(String idEvento, String cpf) {
        Evento evento = buscarEventoPorId(idEvento);
        if (evento != null) {
            return evento.removerInscrito(cpf);
        }
        throw new IllegalArgumentException("Evento com ID " + idEvento + " não encontrado.");
    }

    // Listar a programação de um evento
    public List<Programacao> listarProgramacao(String idEvento) {
        Evento evento = buscarEventoPorId(idEvento);
        if (evento != null) {
            return evento.getProgramacao();
        }
        throw new IllegalArgumentException("Evento com ID " + idEvento + " não encontrado.");
    }

    // Adicionar programação a um evento
    public boolean adicionarProgramacao(String idEvento, Programacao programacao) {
        Evento evento = buscarEventoPorId(idEvento);
        if (evento != null) {
            validarProgramacao(evento, programacao);
            evento.adicionarProgramacao(programacao);
            return true;
        }
        throw new IllegalArgumentException("Evento com ID " + idEvento + " não encontrado.");
    }

    // Atualizar um evento
    public void atualizarEvento(String idEvento, Evento eventoAtualizado) {
        Evento eventoExistente = buscarEventoPorId(idEvento);
        if (eventoExistente != null) {
            if (!eventoExistente.podeSerAtualizado()) {
                throw new IllegalArgumentException("Não é permitido atualizar um evento após sua data de início.");
            }
            eventoExistente.atualizarEvento(eventoAtualizado);
        } else {
            throw new IllegalArgumentException("Evento com ID " + idEvento + " não encontrado.");
        }
    }

    // Remover um evento
    public void removerEvento(String idEvento) {
        Evento evento = buscarEventoPorId(idEvento);
        if (evento == null) {
            throw new IllegalArgumentException("Evento com ID " + idEvento + " não encontrado.");
        }
        if (!evento.podeSerRemovido()) {
            throw new IllegalArgumentException("Não é permitido remover um evento com inscritos ou após sua data de início.");
        }
        eventos.remove(evento);
    }

    // Validações adicionais
    private void validarEvento(Evento evento) {
        if (evento.getNome() == null || evento.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do evento é obrigatório.");
        }
        if (evento.getDescricao() == null || evento.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição do evento é obrigatória.");
        }
        if (evento.getDataInicio() == null || evento.getDataFim() == null) {
            throw new IllegalArgumentException("As datas de início e fim do evento são obrigatórias.");
        }
        if (evento.getNumeroVagas() <= 0) {
            throw new IllegalArgumentException("O número de vagas deve ser maior que zero.");
        }
        if (evento.getCpfResponsavel() == null || evento.getCpfResponsavel().isEmpty()) {
            throw new IllegalArgumentException("O CPF do responsável é obrigatório.");
        }
        if (evento.getEmailResponsavel() == null || evento.getEmailResponsavel().isEmpty()) {
            throw new IllegalArgumentException("O e-mail do responsável é obrigatório.");
        }
    }

    private void validarInscricao(Evento evento, User user) {
        if (evento.getInscritos().stream().anyMatch(u -> u.getCpf().equals(user.getCpf()))) {
            throw new IllegalArgumentException("O usuário já está inscrito neste evento.");
        }
        if (!evento.getDataLimiteInscricao().isEmpty() && LocalDate.parse(evento.getDataLimiteInscricao()).isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A inscrição não pode ser realizada após a data limite.");
        }
    }

    private void validarProgramacao(Evento evento, Programacao programacao) {
        if (programacao.getDataInicioAsLocalDateTime().toLocalDate().isBefore(LocalDate.parse(evento.getDataInicio()))) {
            throw new IllegalArgumentException("A programação não pode começar antes da data de início do evento.");
        }
        if (programacao.getDataFimAsLocalDateTime().toLocalDate().isAfter(LocalDate.parse(evento.getDataFim()))) {
            throw new IllegalArgumentException("A programação não pode terminar após a data de término do evento.");
        }
    }
}
