package com.mycompany.apijavaevents.resources;

import com.mycompany.controller.PalestraController;
import com.mycompany.model.Palestra;
import com.mycompany.model.Participante;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("palestra")
public class PalestraService {

    private final PalestraController palestraController = new PalestraController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Palestra> listarPalestras() {
        return palestraController.listarPalestras();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String criarPalestra(Palestra palestra) {
        palestraController.criarPalestra(palestra);
        return "{\"mensagem\":\"Palestra criada com sucesso\"}";
    }

    @PUT
    @Path("{idPalestra}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String atualizarPalestra(@PathParam("idPalestra") String idPalestra, Palestra palestraAtualizada) {
        try {
            palestraController.atualizarPalestra(idPalestra, palestraAtualizada);
            return "{\"mensagem\":\"Palestra atualizada com sucesso\"}";
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("Erro ao atualizar palestra: " + e.getMessage(), 400);
        }
    }

    @POST
    @Path("{idPalestra}/inscricao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String inscreverParticipante(@PathParam("idPalestra") String idPalestra, Participante participante) {
        boolean sucesso = palestraController.inscreverParticipante(idPalestra, participante);
        if (sucesso) {
            return "{\"mensagem\":\"Inscrição realizada com sucesso\"}";
        } else {
            throw new WebApplicationException("Palestra não encontrada", 404);
        }
    }

    @DELETE
    @Path("{idPalestra}/inscricao/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removerParticipante(@PathParam("idPalestra") String idPalestra, @PathParam("cpf") String cpf) {
        boolean sucesso = palestraController.removerParticipante(idPalestra, cpf);
        if (sucesso) {
            return "{\"mensagem\":\"Participante removido com sucesso\"}";
        } else {
            throw new WebApplicationException("Erro ao remover participante", 404);
        }
    }
}
