package com.mycompany.apijavaevents.resources;

import com.mycompany.apijavaevents.security.Autorizar;
import com.mycompany.controller.InscricaoEventoController;
import com.mycompany.model.InscricaoEvento;
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

@Path("inscricao/evento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Autorizar
public class InscricaoEventoService {
    private InscricaoEventoController inscricaoEventoBC;
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response inscreverUsuarioEvento(InscricaoEvento inscricao) {
        try {
            boolean sucesso = inscricaoEventoBC.inscreverUsuarioEvento(inscricao);

            if (sucesso) {
                return Response.status(Response.Status.CREATED)
                        .entity("{\"mensagem\": \"Usuário inscrito no evento com sucesso.\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"mensagem\": \"Não foi possível inscrever o usuário no evento.\"}")
                        .build();
            }
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"mensagem\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensagem\": \"Erro ao processar a inscrição no evento.\"}")
                    .build();
        }
    }
    
    @GET
    @Path("{eventoId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response listarInscritos(@PathParam("eventoId")int eventoId){
        try{
            List<InscricaoEvento> inscritos = inscricaoEventoBC.listarInscritosPorEvento(eventoId);
            return Response.status(Response.Status.OK).entity(inscritos).build();
        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"mensagem\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensagem\": \"Erro ao listar inscritos.\"}")
                    .build();
        }
    }
    @DELETE
    @Path("/remover/{eventoId}/{usuarioId}")
    @Autorizar
    public Response removerInscricao(@PathParam("usuarioId") int usuarioId, @PathParam("eventoId") int eventoId) {
        try {
            boolean sucesso = inscricaoEventoBC.removerInscricaoUsuario(usuarioId, eventoId);

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
