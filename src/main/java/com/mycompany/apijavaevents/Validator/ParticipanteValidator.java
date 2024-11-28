package com.mycompany.apijavaevents.Validator;

import com.mycompany.model.Participante;

public class ParticipanteValidator {

    public static void validarParticipante(Participante participante) {
        // Validação do CPF
        if (participante.getCpf() == null || !participante.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }

        // Validação do nome
        if (participante.getNome() == null || participante.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do participante é obrigatório.");
        }

        // Validação do e-mail
        if (participante.getEmail() == null || !participante.getEmail().matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }

        // Validação do telefone
        if (participante.getTelefone() == null || !participante.getTelefone().matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone inválido. Deve conter 10 ou 11 dígitos.");
        }
    }
}
