package com.mycompany.controller;

import com.mycompany.model.Palestra;
import com.mycompany.model.Participante;
import java.util.ArrayList;
import java.util.List;

public class PalestraController {

    private static final List<Palestra> palestras = new ArrayList<>();

    public List<Palestra> listarPalestras() {
        return palestras;
    }

    public void criarPalestra(Palestra palestra) {
        palestras.add(palestra);
    }

    public Palestra buscarPalestraPorId(String idPalestra) {
        for (Palestra palestra : palestras) {
            if (palestra.getId().equals(idPalestra)) {
                return palestra;
            }
        }
        return null;
    }

    public boolean inscreverParticipante(String idPalestra, Participante participante) {
        Palestra palestra = buscarPalestraPorId(idPalestra);
        if (palestra != null) {
            palestra.adicionarInscrito(participante);
            return true;
        }
        return false;
    }

    public boolean removerParticipante(String idPalestra, String cpf) {
        Palestra palestra = buscarPalestraPorId(idPalestra);
        if (palestra != null) {
            return palestra.removerInscrito(cpf);
        }
        return false;
    }

    public void atualizarPalestra(String idPalestra, Palestra palestraAtualizada) {
        Palestra palestraExistente = buscarPalestraPorId(idPalestra);

        if (palestraExistente != null) {
            palestraExistente.setNome(palestraAtualizada.getNome());
            palestraExistente.setDescricao(palestraAtualizada.getDescricao());
            palestraExistente.setData(palestraAtualizada.getData());
            palestraExistente.setLocal(palestraAtualizada.getLocal());
            palestraExistente.setPalestrante(palestraAtualizada.getPalestrante());
            palestraExistente.setInscritos(palestraAtualizada.getInscritos());
        } else {
            throw new IllegalArgumentException("Palestra com ID " + idPalestra + " não encontrada.");
        }
    }
}
