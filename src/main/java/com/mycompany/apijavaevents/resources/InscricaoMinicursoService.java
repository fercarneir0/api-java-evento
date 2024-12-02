package com.mycompany.apijavaevents.resources;

import com.mycompany.apijavaevents.security.Autorizar;
import com.mycompany.controller.InscricaoMinicursoController;
import com.mycompany.model.InscricaoMinicurso;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("inscricao/minicurso")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InscricaoMinicursoService {

    private InscricaoMinicursoController controller;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response inscreverUsuarioMinicurso(InscricaoMinicurso inscricao) {
        try {
            boolean sucesso = controller.inscreverUsuarioMinicurso(inscricao);

            if (sucesso) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"mensagem\": \"Usuário inscrito no minicurso com sucesso.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"mensagem\": \"Não foi possível inscrever o usuário no minicurso.\"}")
                        .build();
            }
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"mensagem\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensagem\": \"Erro ao processar a inscrição no minicurso.\"}")
                    .build();
        }
    }

    @GET
    @Path("{minicursoId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response listarInscritosPorMinicurso(@PathParam("minicursoId") int minicursoId) {
        try {
            List<InscricaoMinicurso> inscritos = controller.listarInscritosPorMinicurso(minicursoId);
            return Response.status(Response.Status.OK).entity(inscritos).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"mensagem\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensagem\": \"Erro ao listar inscritos no minicurso.\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/remover/{userId}/{minicursoId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response removerInscricao(@PathParam("userId") int usuarioId, @PathParam("minicursoId") int minicursoId) {
        try {
            boolean sucesso = controller.removerInscricaoUsuario(usuarioId, minicursoId);

            if (sucesso) {
                return Response.status(Response.Status.OK)
                        .entity("{\"mensagem\": \"Inscrição removida com sucesso.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"mensagem\": \"Não foi possível remover a inscrição.\"}")
                        .build();
            }
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"mensagem\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensagem\": \"Erro ao processar a remoção da inscrição.\"}")
                    .build();
        }
    }
}
