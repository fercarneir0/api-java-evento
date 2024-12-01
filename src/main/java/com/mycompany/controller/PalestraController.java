package com.mycompany.controller;

import com.mycompany.model.Palestra;
import com.mycompany.model.Participante;

import java.util.ArrayList;
import java.util.List;

public class PalestraController {
    private final List<Palestra> palestras = new ArrayList<>();

    // Listar todas as palestras
    public List<Palestra> listarPalestras() {
        return palestras;
    }

    // Criar nova palestra
    public void criarPalestra(Palestra palestra) {
        palestras.add(palestra);
    }

    // Atualizar palestra existente
    public void atualizarPalestra(String idPalestra, Palestra palestraAtualizada) {
        Palestra palestraExistente = buscarPalestraPorId(idPalestra);
        if (palestraExistente != null) {
            if (palestraAtualizada.getNome() != null) {
                palestraExistente.setNome(palestraAtualizada.getNome());
            }
            if (palestraAtualizada.getDescricao() != null) {
                palestraExistente.setDescricao(palestraAtualizada.getDescricao());
            }
            if (palestraAtualizada.getData() != null) {
                palestraExistente.setData(palestraAtualizada.getData());
            }
            if (palestraAtualizada.getLocal() != null) {
                palestraExistente.setLocal(palestraAtualizada.getLocal());
            }
            if (palestraAtualizada.getPalestrante() != null) {
                palestraExistente.setPalestrante(palestraAtualizada.getPalestrante());
            }
            if (palestraAtualizada.getTitulo() != null) {
                palestraExistente.setTitulo(palestraAtualizada.getTitulo());
            }
            if (palestraAtualizada.getNumeroVagas() > 0) {
                palestraExistente.setNumeroVagas(palestraAtualizada.getNumeroVagas());
            }
            if (palestraAtualizada.getDataLimiteInscricao() != null) {
                palestraExistente.setDataLimiteInscricao(palestraAtualizada.getDataLimiteInscricao());
            }
        } else {
            throw new IllegalArgumentException("Palestra com ID " + idPalestra + " não encontrada.");
        }
    }

    // Inscrever participante em palestra
    public boolean inscreverParticipante(String idPalestra, Participante participante) {
        Palestra palestra = buscarPalestraPorId(idPalestra);
        if (palestra != null) {
            try {
                palestra.adicionarInscrito(participante);
                return true;
            } catch (IllegalStateException e) {
                return false;
            }
        }
        return false;
    }

    // Remover participante
    public boolean removerParticipante(String idPalestra, String cpf) {
        Palestra palestra = buscarPalestraPorId(idPalestra);
        if (palestra != null) {
            return palestra.removerInscrito(cpf);
        }
        return false;
    }

    // Buscar palestra por ID
    public Palestra buscarPalestraPorId(String idPalestra) {
        return palestras.stream()
                .filter(p -> p.getId().equals(idPalestra))
                .findFirst()
                .orElse(null);
    }
}
