package com.mycompany.apijavaevents.resources;

import com.mycompany.controller.UserBC;
import com.mycompany.model.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import com.mycompany.apijavaevents.security.Autorizar;
import com.mycompany.model.Requester;

@Path("usuario")
public class UserService {

    private UserBC bc = new UserBC();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    private List<User> listarUsuarios(){
        return bc.listarUsuarios();
    }
    
    @POST
    @Path("salvar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    private Response salvarUsuario(User user) {
        boolean sucesso = bc.salvarUsuario(user);
        try {
            if (sucesso) {
                return Response.status(Response.Status.OK)
                    .entity("{\"mensagem\": \"Usuário salvar com sucesso\"}")
                    .build();
            }
            
             return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"mensagem\": \"Não foi possível salvar o usuário\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensagem\": \"Erro: " + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    
    @PUT
    @Path("alterar/{cpf}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    private Response alterarUsuario(String cpf, User user){
        boolean sucesso = bc.alterarUsuario(user);
        
        try{
            if(sucesso){
                return Response.status(Response.Status.OK)
                    .entity("{\"mensagem\": \"Usuário alterado com sucesso\"}")
                    .build();
            }
            
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"mensagem\": \"Você não tem permissão para alterar o usuário\"}")
                    .build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"mensagem\": \"Erro: " + e.getMessage() + "\"}")
                .build();
        }
    }
    
    @DELETE
    @Path("remover")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    private Response removerUsuario(Requester request){
        String email = request.getEmail();
        User user = request.getUser();
        boolean sucesso = bc.removerUsuario(email, user);
        
        try{
            if(sucesso){
                return Response.status(Response.Status.OK)
                    .entity("{\"mensagem\": \"Usuário removido com sucesso\"}")
                    .build();
            }
            
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"mensagem\": \"Você não tem permissão para remover o usuário\"}")
                    .build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"mensagem\": \"Erro: " + e.getMessage() + "\"}")
                .build();
        }
    }
    
    @PUT
    @Path("promover")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    private Response promoverAdministrador(Requester request){
       
       String email = request.getEmail();
       User user = request.getUser();
       boolean sucesso = bc.promoverAdministrador(email, user);
        
        try{
            if(sucesso){
                return Response.status(Response.Status.OK)
                    .entity("{\"mensagem\": \"Usuário promovido a administrador\"}")
                    .build();
            }
            
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"mensagem\": \"Você não tem permissão para promover o usuário\"}")
                    .build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"mensagem\": \"Erro: " + e.getMessage() + "\"}")
                .build();
            } 
    }
}
