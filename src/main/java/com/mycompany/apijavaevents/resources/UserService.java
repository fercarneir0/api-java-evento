package com.mycompany.apijavaevents.resources;

import com.mycompany.apijavaevents.security.Autowired;
import com.mycompany.controller.UserBC;
import com.mycompany.model.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("usuario")
public class UserService {
    
    private UserBC bc = new UserBC();
    
    @POST
    @Path("salvar")
    @Consumes(MediaType.APPLICATION_JSON)
    public void salvarUsuario(User user){
       bc.salvarUsuario(user);
    }
    
    @GET
    @Path("listar")
    @Produces(MediaType.APPLICATION_JSON)
    @Autowired
    public List<User> listarUsuarios(@Context HttpHeaders headers){
        return bc.listarUsuarios();
    }
    
    @DELETE
    @Path("remover/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void removerUsuario(User email){
        bc.removerUsuarios(email);
    }
}
