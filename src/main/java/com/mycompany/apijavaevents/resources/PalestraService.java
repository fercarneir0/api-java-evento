package com.mycompany.apijavaevents.resources;

import com.mycompany.controller.PalestraController;
import com.mycompany.model.Palestra;
import com.mycompany.model.Participante;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("palestra")
public class PalestraService {

    private final PalestraController palestraController = new PalestraController();

    // Listar todas as palestras
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPalestras() {
        try {
            List<Palestra> palestras = palestraController.listarPalestras();
            if (palestras.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Nenhuma palestra encontrada\"}").build();
            }
            return Response.ok(palestras).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao listar palestras\"}").build();
        }
    }

    // Criar uma nova palestra
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarPalestra(Palestra palestra) {
        try {
            palestraController.criarPalestra(palestra);
            return Response.status(Response.Status.CREATED).entity("{\"msg\":\"Palestra criada com sucesso\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(422).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao criar palestra\"}").build();
        }
    }

    // Atualizar uma palestra existente
    @PUT
    @Path("{idPalestra}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarPalestra(@PathParam("idPalestra") String idPalestra, Palestra palestraAtualizada) {
        try {
            palestraController.atualizarPalestra(idPalestra, palestraAtualizada);
            return Response.ok("{\"msg\":\"Palestra atualizada com sucesso\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(422).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao atualizar palestra\"}").build();
        }
    }

    // Inscrever um participante em uma palestra
    @POST
    @Path("{idPalestra}/inscricao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inscreverParticipante(@PathParam("idPalestra") String idPalestra, Participante participante) {
        try {
            boolean sucesso = palestraController.inscreverParticipante(idPalestra, participante);
            if (!sucesso) {
                return Response.status(422).entity("{\"msg\":\"Palestra não encontrada ou número de vagas excedido\"}").build();
            }
            return Response.ok("{\"msg\":\"Inscrição realizada com sucesso\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao inscrever participante\"}").build();
        }
    }

    // Remover um participante inscrito de uma palestra
    @DELETE
    @Path("{idPalestra}/inscricao/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerParticipante(@PathParam("idPalestra") String idPalestra, @PathParam("cpf") String cpf) {
        try {
            boolean sucesso = palestraController.removerParticipante(idPalestra, cpf);
            if (!sucesso) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Participante não encontrado\"}").build();
            }
            return Response.ok("{\"msg\":\"Participante removido com sucesso\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao remover participante\"}").build();
        }
    }
}
