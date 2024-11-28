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

    public Palestra buscarPalestraPorId(String idPalestra) {
        for (Palestra palestra : palestras) {
            if (palestra.getId().equals(idPalestra)) {
                return palestra;
            }
        }
        return null;
    }

    public boolean removerParticipante(String idPalestra, String cpf) {
        Palestra palestra = buscarPalestraPorId(idPalestra);
        if (palestra != null) {
            return palestra.removerInscrito(cpf);
        }
        return false;
    }
}
