package com.mycompany.apijavaevents.resources;

import com.mycompany.apijavaevents.security.Autorizar;
import com.mycompany.controller.MinicursoController;
import com.mycompany.model.Minicurso;
import com.mycompany.model.Participante;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("minicurso")
public class MinicursoService {

    private final MinicursoController minicursoController = new MinicursoController();

    // Listar todos os minicursos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response listarMinicursos() {
        try {
            List<Minicurso> minicursos = minicursoController.listarMinicursos();
            if (minicursos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Nenhum minicurso encontrado\"}").build();
            }
            return Response.ok(minicursos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao listar minicursos\"}").build();
        }
    }

    // Criar um novo minicurso
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response criarMinicurso(Minicurso minicurso) {
        try {
            minicursoController.criarMinicurso(minicurso);
            return Response.status(Response.Status.CREATED).entity("{\"msg\":\"Minicurso criado com sucesso\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(422).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao criar minicurso\"}").build();
        }
    }

    // Atualizar um minicurso
    @PUT
    @Path("{idMinicurso}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response atualizarMinicurso(@PathParam("idMinicurso") String idMinicurso, Minicurso minicursoAtualizado) {
        try {
            minicursoController.atualizarMinicurso(idMinicurso, minicursoAtualizado);
            return Response.ok("{\"msg\":\"Minicurso atualizado com sucesso\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(422).entity("{\"msg\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao atualizar minicurso\"}").build();
        }
    }

    // Inscrever um participante em um minicurso
    @POST
    @Path("{idMinicurso}/inscricao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response inscreverParticipante(@PathParam("idMinicurso") String idMinicurso, Participante participante) {
        try {
            boolean sucesso = minicursoController.inscreverParticipante(idMinicurso, participante);
            if (!sucesso) {
                return Response.status(422).entity("{\"msg\":\"Minicurso não encontrado ou número máximo de vagas atingido\"}").build();
            }
            return Response.ok("{\"msg\":\"Inscrição realizada com sucesso\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao inscrever participante\"}").build();
        }
    }

    // Obter participantes inscritos em um minicurso
    @GET
    @Path("{idMinicurso}/inscritos")
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response obterInscritos(@PathParam("idMinicurso") String idMinicurso) {
        try {
            Minicurso minicurso = minicursoController.buscarMinicursoPorId(idMinicurso);
            if (minicurso == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Minicurso não encontrado\"}").build();
            }
            List<Participante> inscritos = minicurso.getInscritos();
            if (inscritos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Nenhum participante inscrito neste minicurso\"}").build();
            }
            return Response.ok(inscritos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao buscar inscritos\"}").build();
        }
    }

    // Remover um participante inscrito de um minicurso
    @DELETE
    @Path("{idMinicurso}/inscricao/{cpf}")
    @Autorizar
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerParticipante(@PathParam("idMinicurso") String idMinicurso, @PathParam("cpf") String cpf) {
        try {
            boolean sucesso = minicursoController.removerParticipante(idMinicurso, cpf);
            if (!sucesso) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"msg\":\"Participante não encontrado\"}").build();
            }
            return Response.ok("{\"msg\":\"Participante removido com sucesso\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Erro ao remover participante\"}").build();
        }
    }
}
