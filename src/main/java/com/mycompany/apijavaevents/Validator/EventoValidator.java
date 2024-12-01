package com.mycompany.apijavaevents.Validator;

import com.mycompany.model.Evento;
import java.time.LocalDate;

public class EventoValidator {

    public static void validarEvento(Evento evento) {
        if (evento.getNome() == null || evento.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do evento é obrigatório.");
        }
        if (evento.getDescricao() == null || evento.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição do evento é obrigatória.");
        }
        if (evento.getDataInicio() == null || evento.getDataFim() == null) {
            throw new IllegalArgumentException("As datas de início e fim do evento são obrigatórias.");
        }
        if (evento.getNumeroVagas() <= 0) {
            throw new IllegalArgumentException("O número de vagas deve ser maior que zero.");
        }
        if (evento.getDataLimiteInscricao() == null) {
            throw new IllegalArgumentException("A data limite de inscrição é obrigatória.");
        }
        if (evento.getCpfResponsavel() == null || !evento.getCpfResponsavel().matches("\\d{11}")) {
            throw new IllegalArgumentException("O CPF do responsável é inválido ou está ausente.");
        }
        if (evento.getEmailResponsavel() == null || !evento.getEmailResponsavel().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("O e-mail do responsável é inválido ou está ausente.");
        }

        LocalDate dataInicio = LocalDate.parse(evento.getDataInicio());
        LocalDate dataFim = LocalDate.parse(evento.getDataFim());
        LocalDate dataLimiteInscricao = LocalDate.parse(evento.getDataLimiteInscricao());

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("A data de início deve ser anterior ou igual à data de fim.");
        }
        if (dataLimiteInscricao.isAfter(dataInicio)) {
            throw new IllegalArgumentException("A data limite de inscrição deve ser anterior à data de início.");
        }
    }
}
