package com.mycompany.controller;

import com.mycompany.model.Minicurso;
import com.mycompany.model.Participante;
import com.mycompany.model.Programacao;
import java.util.ArrayList;
import java.util.List;

public class MinicursoController {

    private static final List<Minicurso> minicursos = new ArrayList<>();

    public List<Minicurso> listarMinicursos() {
        return minicursos;
    }

    public void criarMinicurso(Minicurso minicurso) {
        minicursos.add(minicurso);
    }

    public boolean inscreverParticipante(String idMinicurso, Participante participante) {
        Minicurso minicurso = buscarMinicursoPorId(idMinicurso);
        if (minicurso != null) {
            try {
                minicurso.adicionarInscrito(participante);
                return true;
            } catch (IllegalStateException e) {
                return false;
            }
        }
        return false;
    }

    public Minicurso buscarMinicursoPorId(String idMinicurso) {
        for (Minicurso minicurso : minicursos) {
            if (minicurso.getId().equals(idMinicurso)) {
                return minicurso;
            }
        }
        return null;
    }

    public boolean removerParticipante(String idMinicurso, String cpf) {
        Minicurso minicurso = buscarMinicursoPorId(idMinicurso);
        if (minicurso != null) {
            return minicurso.removerInscrito(cpf);
        }
        return false;
    }

    public List<Programacao> listarProgramacao(String idMinicurso) {
        Minicurso minicurso = buscarMinicursoPorId(idMinicurso);
        if (minicurso != null) {
            return minicurso.getProgramacao();
        }
        return new ArrayList<>();
    }

    public boolean adicionarProgramacao(String idMinicurso, Programacao programacao) {
        Minicurso minicurso = buscarMinicursoPorId(idMinicurso);
        if (minicurso != null) {
            minicurso.adicionarProgramacao(programacao);
            return true;
        }
        return false;
    }

    // Método para atualizar o minicurso
    public void atualizarMinicurso(String idMinicurso, Minicurso minicursoAtualizado) {
        Minicurso minicursoExistente = buscarMinicursoPorId(idMinicurso);

        if (minicursoExistente != null) {
            // Atualiza os dados do minicurso existente com os dados do minicurso atualizado
            minicursoExistente.setNome(minicursoAtualizado.getNome());
            minicursoExistente.setDescricao(minicursoAtualizado.getDescricao());
            minicursoExistente.setData(minicursoAtualizado.getData());
            minicursoExistente.setLocal(minicursoAtualizado.getLocal());
            minicursoExistente.setCapacidade(minicursoAtualizado.getCapacidade());
            minicursoExistente.setProgramacao(minicursoAtualizado.getProgramacao());
            minicursoExistente.setInscritos(minicursoAtualizado.getInscritos());
        } else {
            throw new IllegalArgumentException("Minicurso com ID " + idMinicurso + " não encontrado.");
        }
    }
}