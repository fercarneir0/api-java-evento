package com.mycompany.controller;

import com.mycompany.apijavaevents.repository.InscricaoMinicursoDAO;
import com.mycompany.model.InscricaoMinicurso;
import java.util.Date;
import java.util.List;

public class InscricaoMinicursoController {

    private InscricaoMinicursoDAO inscricaoMinicurso;

    public boolean inscreverUsuarioMinicurso(InscricaoMinicurso inscricao) {
        // Obter período completo do minicurso
        Date[] periodo = inscricaoMinicurso.obterPeriodoMinicurso(inscricao.getMinicursoId());
        Date dataAtual = new Date();

        // Valida data e hora de início
        if (dataAtual.before(periodo[0]) || dataAtual.after(periodo[2])) {
            throw new IllegalStateException("As inscrições só podem ocorrer entre as datas de início e fim do minicurso.");
        }

        // Valida hora de início e fim
        if (dataAtual.equals(periodo[0]) && dataAtual.before(periodo[1])) {
            throw new IllegalStateException("Inscrições não permitidas antes do horário de início do minicurso.");
        }

        if (dataAtual.equals(periodo[2]) && dataAtual.after(periodo[3])) {
            throw new IllegalStateException("Inscrições não permitidas após o horário de término do minicurso.");
        }

        // Verifica se há vagas disponíveis
        int vagasRestantes = inscricaoMinicurso.obterNumeroVagasRestantes(inscricao.getMinicursoId());
        if (vagasRestantes <= 0) {
            throw new IllegalStateException("O minicurso já está com todas as vagas preenchidas.");
        }

        // Define a data da inscrição como o momento atual
        inscricao.setDataInscricao(dataAtual);

        // Inscreve o usuário no minicurso
        return inscricaoMinicurso.inscreverUsuarioMinicurso(inscricao);
    }

    public List<InscricaoMinicurso> listarInscritosPorMinicurso(int minicursoId) {
        if (minicursoId <= 0) {
            throw new IllegalArgumentException("ID do minicurso inválido.");
        }

        // Chama o DAO para buscar os inscritos no banco de dados
        return inscricaoMinicurso.listarInscritosPorMinicurso(minicursoId);
    }

    public boolean removerInscricaoUsuario(int usuarioId, int minicursoId) {
        // Obter período do minicurso
        Date[] periodo = inscricaoMinicurso.obterPeriodoMinicurso(minicursoId);
        Date agora = new Date();

        // Verifica se a remoção está sendo feita antes do início do minicurso
        if (!agora.before(periodo[0])) {
            throw new IllegalStateException("A inscrição só pode ser removida antes do início do minicurso.");
        }

        // Remove a inscrição
        return inscricaoMinicurso.removerInscricao(usuarioId, minicursoId);
    }
}
