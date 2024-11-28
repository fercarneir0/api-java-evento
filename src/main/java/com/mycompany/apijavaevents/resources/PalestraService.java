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

    @POST
    @Path("{idPalestra}/inscricao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String inscreverParticipante(@PathParam("idPalestra") String idPalestra, Participante participante) {
        boolean sucesso = palestraController.inscreverParticipante(idPalestra, participante);
        if (sucesso) {
            return "{\"mensagem\":\"Inscrição realizada com sucesso\"}";
        } else {
            throw new WebApplicationException("Palestra não encontrada ou número máximo de vagas atingido", 404);
        }
    }

    @GET
    @Path("{idPalestra}/inscritos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Participante> obterInscritos(@PathParam("idPalestra") String idPalestra) {
        Palestra palestra = palestraController.buscarPalestraPorId(idPalestra);
        if (palestra == null) {
            throw new WebApplicationException("Palestra não encontrada", 404);
        }
        return palestra.getInscritos();
    }

    @DELETE
    @Path("{idPalestra}/inscricao/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removerParticipante(@PathParam("idPalestra") String idPalestra, @PathParam("cpf") String cpf) {
        boolean sucesso = palestraController.removerParticipante(idPalestra, cpf);
        if (sucesso) {
            return "{\"mensagem\":\"Participante removido com sucesso\"}";
        } else {
            throw new WebApplicationException("Participante não encontrado", 404);
        }
    }
}
