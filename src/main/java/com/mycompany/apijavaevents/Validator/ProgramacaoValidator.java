package com.mycompany.apijavaevents.Validator;


import com.mycompany.model.Programacao;

public class ProgramacaoValidator {

    public static void validarProgramacao(Programacao programacao) {
        if (programacao.getNome() == null || programacao.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome da programação é obrigatório.");
        }
        if (programacao.getDataInicio() == null || programacao.getDataFim() == null) {
            throw new IllegalArgumentException("As datas de início e fim da programação são obrigatórias.");
        }

        try {
            if (programacao.getDataInicioAsLocalDateTime().isAfter(programacao.getDataFimAsLocalDateTime())) {
                throw new IllegalArgumentException("A data de início deve ser anterior ou igual à data de fim.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao processar as datas. Verifique o formato (yyyy-MM-dd'T'HH:mm:ss).", e);
        }
    }
}
