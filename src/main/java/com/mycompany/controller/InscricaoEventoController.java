package com.mycompany.controller;

import com.mycompany.apijavaevents.repository.InscricaoEventoDAO;
import com.mycompany.model.InscricaoEvento;
import java.util.Date;
import java.util.List;

public class InscricaoEventoController {
    private final InscricaoEventoDAO inscricaoEventoDAO = new InscricaoEventoDAO();

    public boolean inscreverUsuarioEvento(InscricaoEvento inscricao) {
        // Verifica a data limite do evento
        Date dataLimite = inscricaoEventoDAO.obterDataLimiteEvento(inscricao.getEventoId());
        Date dataAtual = new Date();

        if (dataAtual.after(dataLimite)) {
            throw new IllegalStateException("As inscrições para este evento já foram encerradas.");
        }

        // Verifica se o número de vagas restantes é maior que zero
        int vagasRestantes = inscricaoEventoDAO.obterNumeroVagasRestantes(inscricao.getEventoId());
        if (vagasRestantes <= 0) {
            throw new IllegalStateException("O evento já está com todas as vagas preenchidas.");
        }

        // Verifica se o usuário já está inscrito no evento
        if (inscricaoEventoDAO.verificarInscricao(inscricao.getUserId(), inscricao.getEventoId())) {
            throw new IllegalStateException("Usuário já está inscrito no evento.");
        }

        // Define a data da inscrição como o momento atual
        inscricao.setDataInscricao(dataAtual);

        // Inscreve o usuário no evento
        return inscricaoEventoDAO.inscreverUsuarioEvento(inscricao);
    }

    public boolean removerInscricaoUsuario(int usuarioId, int eventoId) {
        // Obter a data de início do evento
        Date dataInicio = inscricaoEventoDAO.obterDataInicioEvento(eventoId);
        Date agora = new Date();

        // Verifica se a remoção está sendo feita com pelo menos 24 horas de antecedência
        long diferencaHoras = (dataInicio.getTime() - agora.getTime()) / (60 * 60 * 1000); // Diferença em horas
        if (diferencaHoras < 24) {
            throw new IllegalStateException("A inscrição só pode ser removida até 24 horas antes do início do evento.");
        }

        // Remove a inscrição do banco de dados
        return inscricaoEventoDAO.removerInscricao(usuarioId, eventoId);
    }
    public List<InscricaoEvento> listarInscritosPorEvento(int eventoId) {
        if (eventoId <= 0) {
            throw new IllegalArgumentException("ID do evento inválido.");
        }

        // Chama o DAO para buscar os inscritos no banco de dados
        return inscricaoEventoDAO.listarInscritosPorEvento(eventoId);
    }
}


