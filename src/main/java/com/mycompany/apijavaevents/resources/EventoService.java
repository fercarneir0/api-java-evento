package com.mycompany.apijavaevents.resources;


import com.mycompany.apijavaevents.Validator.EventoValidator;
import com.mycompany.apijavaevents.Validator.ParticipanteValidator;
import com.mycompany.apijavaevents.Validator.ProgramacaoValidator;
import com.mycompany.controller.EventoController;
import com.mycompany.model.Evento;
import com.mycompany.model.Participante;
import com.mycompany.model.Programacao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("evento")
public class EventoService {

    private final EventoController eventoController = new EventoController();

    // Listar todos os eventos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Evento> listarEventos() {
        List<Evento> eventos = eventoController.listarEventos();
        return eventos;
    }

    // Criar um novo evento
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String criarEvento(Evento evento) {
        try {
            EventoValidator.validarEvento(evento);
            eventoController.criarEvento(evento);
            return "{\"mensagem\":\"Evento criado com sucesso\"}";
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("Erro ao criar evento: " + e.getMessage(), 400);
        }
    }
    // Inscrever um participante em um evento

    @POST
    @Path("{idEvento}/inscricao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String inscreverParticipante(@PathParam("idEvento") String idEvento, Participante participante) {
        try {
            ParticipanteValidator.validarParticipante(participante);
            boolean sucesso = eventoController.inscreverParticipante(idEvento, participante);
            if (!sucesso) {
                throw new WebApplicationException("Número máximo de vagas atingido ou evento não encontrado", 404);
            }
            return "{\"mensagem\":\"Inscrição realizada com sucesso\"}";
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("Erro ao inscrever participante: " + e.getMessage(), 400);
        }
    }
    // Obter participantes inscritos em um evento

    @GET
    @Path("{idEvento}/inscritos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Participante> obterInscritos(@PathParam("idEvento") String idEvento) {
        Evento evento = eventoController.buscarEventoPorId(idEvento);
        if (evento == null) {
            throw new WebApplicationException("Evento não encontrado", 404);
        }
        List<Participante> inscritos = evento.getInscritos();
        if (inscritos.isEmpty()) {
            throw new WebApplicationException("Nenhum participante inscrito neste evento", 404);
        }
        return inscritos;
    }
    // Remover um participante inscrito de um evento

    @DELETE
    @Path("{idEvento}/inscricao/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removerParticipante(@PathParam("idEvento") String idEvento, @PathParam("cpf") String cpf) {
        Evento evento = eventoController.buscarEventoPorId(idEvento);
        if (evento == null) {
            throw new WebApplicationException("Evento não encontrado", 404);
        }
        boolean sucesso = eventoController.removerParticipante(idEvento, cpf);
        if (!sucesso) {
            throw new WebApplicationException("Participante não encontrado", 404);
        }
        return "{\"mensagem\":\"Participante removido com sucesso\"}";
    }
    // Adicionar programação a um evento

    @POST
    @Path("{idEvento}/programacao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String adicionarProgramacao(@PathParam("idEvento") String idEvento, Programacao programacao) {
        try {
            ProgramacaoValidator.validarProgramacao(programacao);
            boolean sucesso = eventoController.adicionarProgramacao(idEvento, programacao);
            if (!sucesso) {
                throw new WebApplicationException("Evento não encontrado", 404);
            }
            return "{\"mensagem\":\"Programação adicionada com sucesso\"}";
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("Erro ao adicionar programação: " + e.getMessage(), 400);
        }
    }
    // Listar programação de um evento

    @GET
    @Path("{idEvento}/programacao")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Programacao> listarProgramacao(@PathParam("idEvento") String idEvento) {
        Evento evento = eventoController.buscarEventoPorId(idEvento);
        if (evento == null) {
            throw new WebApplicationException("Evento não encontrado", 404);
        }
        List<Programacao> programacao = evento.getProgramacao();
        if (programacao.isEmpty()) {
            throw new WebApplicationException("Nenhuma programação encontrada para este evento", 404);
        }
        return programacao;
    }
}
