package com.mycompany.controller;

import com.mycompany.model.Evento;
import com.mycompany.model.Participante;
import com.mycompany.model.Programacao;

import java.util.ArrayList;
import java.util.List;

public class EventoController {

    private static final List<Evento> eventos = new ArrayList<>();

    public List<Evento> listarEventos() {
        return eventos;
    }

    public void criarEvento(Evento evento) {
        eventos.add(evento);
    }

    public boolean inscreverParticipante(String idEvento, Participante participante) {
        Evento evento = buscarEventoPorId(idEvento);
        if (evento != null) {
            try {
                evento.adicionarInscrito(participante);
                return true;
            } catch (IllegalStateException e) {
                return false;
            }
        }
        return false;
    }

    public Evento buscarEventoPorId(String idEvento) {
        return eventos.stream()
                .filter(evento -> evento.getId().equals(idEvento))
                .findFirst()
                .orElse(null);
    }

    public boolean removerParticipante(String idEvento, String cpf) {
        Evento evento = buscarEventoPorId(idEvento);
        if (evento != null) {
            return evento.removerInscrito(cpf);
        }
        return false;
    }

    public List<Programacao> listarProgramacao(String idEvento) {
        Evento evento = buscarEventoPorId(idEvento);
        return (evento != null) ? evento.getProgramacao() : new ArrayList<>();
    }

    public boolean adicionarProgramacao(String idEvento, Programacao programacao) {
        Evento evento = buscarEventoPorId(idEvento);
        if (evento != null) {
            evento.adicionarProgramacao(programacao);
            return true;
        }
        return false;
    }

    // Método de atualização
    public void atualizarEvento(String idEvento, Evento eventoAtualizado) {
        Evento eventoExistente = buscarEventoPorId(idEvento);
        if (eventoExistente != null) {
            // Atualiza o evento existente com as informações do evento atualizado
            eventoExistente.atualizarEvento(eventoAtualizado);
        } else {
            throw new IllegalArgumentException("Evento com ID " + idEvento + " não encontrado.");
        }
    }
}