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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import com.mycompany.apijavaevents.security.Autorizar;
import com.mycompany.controller.LoginController;
import com.mycompany.model.Login;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.container.ContainerRequestContext;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.crypto.SecretKey;

@Path("usuario")
public class UserService {

    private UserBC bc = new UserBC();

    private final SecretKey CHAVE = Keys.hmacShaKeyFor(System.getenv("CHAVE").getBytes(StandardCharsets.UTF_8));

    @Context
    private ContainerRequestContext context;

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logar(Login login) {
        try {
            LoginController controller = new LoginController();

            User user = controller.autenticarUsuario(login);

            if (user != null) {
                String jwtToken = Jwts.builder()
                        .setSubject(user.getEmail())
                        .claim("id", user.getId())
                        .claim("admin", user.isAdmin())
                        .setIssuedAt(new Date()).setExpiration(Date.from(LocalDateTime.now().plusMinutes(15L)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                        .signWith(CHAVE, SignatureAlgorithm.HS512)
                        .compact();
                return Response.status(Response.Status.OK).entity(jwtToken).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário e/ou senha inválidos").build();
            }
        } catch (InvalidKeyException ex) {
            System.out.println(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response listarUsuarios() {
        Integer obterIdDoToken = (Integer) context.getProperty("userId");
        Boolean isAdmin = (Boolean) context.getProperty("admin");

        if (obterIdDoToken == null || (isAdmin != null && !isAdmin)) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"mensagem\": \"Você não tem permissão para listar os usuários.\"}")
                    .build();
        }
        return Response.status(Response.Status.OK).entity(bc.listarUsuarios()).build();
    }

    @POST
    @Path("salvar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response salvarUsuario(User user) {
        boolean sucesso = bc.salvarUsuario(user);
        try {
            if (sucesso) {
                return Response.status(Response.Status.OK)
                        .entity("{\"mensagem\": \"Usuário salvo com sucesso\"}")
                        .build();
            }

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"mensagem\": \"Não foi possível salvar o usuário\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensagem\": \"Erro: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("alterar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response alterarUsuario(@PathParam("id") int id, User user) {
        try {
            Integer obterIdDoToken = (Integer) context.getProperty("userId");

            if (obterIdDoToken == null) {
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("{\"mensagem\": \"Usuário não encontrado\"}")
                        .build();
            }

            if (!obterIdDoToken.equals(id)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"mensagem\": \"Você não tem permissão para alterar este usuário.\"}")
                        .build();
            }
            boolean sucesso = bc.alterarUsuario(id, user);

            if (sucesso) {
                return Response.status(Response.Status.OK)
                        .entity("{\"mensagem\": \"Usuário alterado com sucesso\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"mensagem\": \"Dados do usuário são inválidos.\"}")
                        .build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensagem\": \"Erro: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("remover/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response removerUsuario(@PathParam("id") int id) {

        try {
            Integer obterIdDoToken = (Integer) context.getProperty("userId");

            if (obterIdDoToken == null) {
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("{\"mensagem\": \"Usuário não encontrado\"}")
                        .build();
            }

            if (!obterIdDoToken.equals(id)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"mensagem\": \"Você não tem permissão para remover este usuário\"}")
                        .build();
            }
            boolean sucesso = bc.removerUsuario(id);

            if (sucesso) {
                return Response.status(Response.Status.OK)
                        .entity("{\"mensagem\": \"Usuário removido com sucesso\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"mensagem\": \"Dados do usuário são inválidos.\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensagem\": \"Erro: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("promover/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Autorizar
    public Response promoverAdministrador(@PathParam("id") int id, User user) {
        try {
            Integer obterIdDoToken = (Integer) context.getProperty("userId");

            if (obterIdDoToken == null) {
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("{\"mensagem\": \"Usuário não encontrado\"}")
                        .build();
            }

            if (!obterIdDoToken.equals(id)) {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"mensagem\": \"Você não tem permissão para alterar este usuário.\"}")
                        .build();
            }
            boolean sucesso = bc.promoverAdministrador(id, user);

            if (sucesso) {
                return Response.status(Response.Status.OK)
                        .entity("{\"mensagem\": \"Usuário promovido a administrador\"}")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"mensagem\": \"Dados do usuário são inválidos.\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensagem\": \"Erro: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}
