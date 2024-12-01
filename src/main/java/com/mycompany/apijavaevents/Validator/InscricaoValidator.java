package com.mycompany.apijavaevents.Validator;

import com.mycompany.model.Participante;
import java.time.LocalDateTime;

public class InscricaoValidator {

    public static void validarInscricao(Participante participante, LocalDateTime dataLimite) {
        ParticipanteValidator.validarParticipante(participante);

        LocalDateTime agora = LocalDateTime.now();
        if (dataLimite != null && agora.isAfter(dataLimite)) {
            throw new IllegalArgumentException("Inscrição não pode ser realizada após a data limite.");
        }
    }
}
