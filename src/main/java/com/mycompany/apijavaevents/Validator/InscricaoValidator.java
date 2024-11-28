package com.mycompany.apijavaevents.Validator;


import com.mycompany.model.Participante;

public class InscricaoValidator {

    public static void validarInscricao(Participante participante) {
        if (participante.getCpf() == null || !participante.getCpf().matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new IllegalArgumentException("O CPF do participante é inválido ou está ausente.");
        }
        if (participante.getNome() == null || participante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do participante é obrigatório.");
        }
        if (participante.getEmail() == null || !participante.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("O e-mail do participante é inválido ou está ausente.");
        }
        if (participante.getTelefone() == null || !participante.getTelefone().matches("\\(\\d{2}\\) \\d{4,5}-\\d{4}")) {
            throw new IllegalArgumentException("O telefone do participante é inválido ou está ausente. Use o formato (XX) XXXXX-XXXX.");
        }
    }
}
