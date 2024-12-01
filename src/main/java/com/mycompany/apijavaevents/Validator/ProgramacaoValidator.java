package com.mycompany.apijavaevents.Validator;

import com.mycompany.model.Programacao;

import java.time.LocalDateTime;

public class ProgramacaoValidator {

    public static void validarProgramacao(Programacao programacao) {
        if (programacao.getNome() == null || programacao.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome da programação é obrigatório.");
        }
        if (programacao.getDataInicio() == null || programacao.getDataFim() == null) {
            throw new IllegalArgumentException("As datas de início e fim da programação são obrigatórias.");
        }

        LocalDateTime inicio = programacao.getDataInicioAsLocalDateTime();
        LocalDateTime fim = programacao.getDataFimAsLocalDateTime();

        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("A data de início deve ser anterior ou igual à data de fim.");
        }
    }
}
