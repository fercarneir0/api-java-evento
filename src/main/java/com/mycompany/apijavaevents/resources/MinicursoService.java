package com.mycompany.apijavaevents.resources;

import com.mycompany.controller.MinicursoController;
import com.mycompany.model.Minicurso;
import com.mycompany.model.Participante;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("minicurso")
public class MinicursoService {

    private final MinicursoController minicursoController = new MinicursoController();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Minicurso> listarMinicursos() {
        return minicursoController.listarMinicursos();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String criarMinicurso(Minicurso minicurso) {
        minicursoController.criarMinicurso(minicurso);
        return "{\"mensagem\":\"Minicurso criado com sucesso\"}";
    }

    @PUT
    @Path("{idMinicurso}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String atualizarMinicurso(@PathParam("idMinicurso") String idMinicurso, Minicurso minicursoAtualizado) {
        try {
            minicursoController.atualizarMinicurso(idMinicurso, minicursoAtualizado);
            return "{\"mensagem\":\"Minicurso atualizado com sucesso\"}";
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("Erro ao atualizar minicurso: " + e.getMessage(), 400);
        }
    }

    @POST
    @Path("{idMinicurso}/inscricao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String inscreverParticipante(@PathParam("idMinicurso") String idMinicurso, Participante participante) {
        boolean sucesso = minicursoController.inscreverParticipante(idMinicurso, participante);
        if (sucesso) {
            return "{\"mensagem\":\"Inscrição realizada com sucesso\"}";
        } else {
            throw new WebApplicationException("Minicurso não encontrado ou número máximo de vagas atingido", 404);
        }
    }

    @GET
    @Path("{idMinicurso}/inscritos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Participante> obterInscritos(@PathParam("idMinicurso") String idMinicurso) {
        Minicurso minicurso = minicursoController.buscarMinicursoPorId(idMinicurso);
        if (minicurso == null) {
            throw new WebApplicationException("Minicurso não encontrado", 404);
        }
        return minicurso.getInscritos();
    }

    @DELETE
    @Path("{idMinicurso}/inscricao/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removerParticipante(@PathParam("idMinicurso") String idMinicurso, @PathParam("cpf") String cpf) {
        boolean sucesso = minicursoController.removerParticipante(idMinicurso, cpf);
        if (sucesso) {
            return "{\"mensagem\":\"Participante removido com sucesso\"}";
        } else {
            throw new WebApplicationException("Erro ao remover participante", 404);
        }
    }
}
