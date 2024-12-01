package com.mycompany.apijavaevents.resources;

import com.mycompany.controller.EventoController;
import com.mycompany.model.Evento;
import com.mycompany.model.Participante;
import com.mycompany.model.Programacao;
import com.mycompany.apijavaevents.Validator.EventoValidator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("evento")
public class EventoService {

    private final EventoController eventoController = new EventoController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEventos() {
        try {
            List<Evento> eventos = eventoController.listarEventos();
            if (eventos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Nenhum evento encontrado\"}").build();
            }
            return Response.ok(eventos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao listar eventos\"}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarEvento(Evento evento) {
        try {
            // Chama a validação antes de criar o evento
            EventoValidator.validarEvento(evento);
            eventoController.criarEvento(evento);
            return Response.status(Response.Status.CREATED).entity("{\"msg\":\"Evento criado com sucesso\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(422).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro interno no servidor\"}").build();
        }
    }

    @PUT
    @Path("{idEvento}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarEvento(@PathParam("idEvento") String idEvento, Evento evento) {
        try {
            Evento eventoExistente = eventoController.buscarEventoPorId(idEvento);
            if (eventoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Evento não encontrado\"}").build();
            }

            // Validação adicional antes de atualizar
            EventoValidator.validarEvento(evento);
            eventoController.atualizarEvento(idEvento, evento);
            return Response.ok("{\"msg\":\"Evento atualizado com sucesso\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(422).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro interno no servidor\"}").build();
        }
    }

    @POST
    @Path("{idEvento}/inscricao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inscreverParticipante(@PathParam("idEvento") String idEvento, Participante participante) {
        try {
            boolean sucesso = eventoController.inscreverParticipante(idEvento, participante);
            if (!sucesso) {
                return Response.status(422).entity("{\"msg\":\"Número máximo de vagas atingido ou evento não encontrado\"}").build();
            }
            return Response.ok("{\"msg\":\"Inscrição realizada com sucesso\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(422).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro interno no servidor\"}").build();
        }
    }

    @DELETE
    @Path("{idEvento}/inscricao/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerParticipante(@PathParam("idEvento") String idEvento, @PathParam("cpf") String cpf) {
        try {
            Evento evento = eventoController.buscarEventoPorId(idEvento);
            if (evento == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Evento não encontrado\"}").build();
            }
            boolean sucesso = eventoController.removerParticipante(idEvento, cpf);
            if (!sucesso) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Participante não encontrado\"}").build();
            }
            return Response.ok("{\"msg\":\"Participante removido com sucesso\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao remover participante\"}").build();
        }
    }

    @POST
    @Path("{idEvento}/programacao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response adicionarProgramacao(@PathParam("idEvento") String idEvento, Programacao programacao) {
        try {
            boolean sucesso = eventoController.adicionarProgramacao(idEvento, programacao);
            if (!sucesso) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Evento não encontrado\"}").build();
            }
            return Response.ok("{\"msg\":\"Programação adicionada com sucesso\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(422).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao adicionar programação\"}").build();
        }
    }

    @GET
    @Path("{idEvento}/programacao")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProgramacao(@PathParam("idEvento") String idEvento) {
        try {
            Evento evento = eventoController.buscarEventoPorId(idEvento);
            if (evento == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Evento não encontrado\"}").build();
            }
            List<Programacao> programacao = evento.getProgramacao();
            if (programacao.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Nenhuma programação encontrada para este evento\"}").build();
            }
            return Response.ok(programacao).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao listar programação\"}").build();
        }
    }
}
