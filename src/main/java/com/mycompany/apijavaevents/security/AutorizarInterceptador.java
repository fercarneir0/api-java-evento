package com.mycompany.apijavaevents.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

@Provider
@Autorizar
public class AutorizarInterceptador implements ContainerRequestFilter {

    private final SecretKey CHAVE = Keys.hmacShaKeyFor(System.getenv("CHAVE").getBytes());

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String token = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (token == null || !token.trim().toLowerCase().startsWith("bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"mensagem\": \"Token não fornecido ou formato inválido.\"}")
                    .build());
            return;
        }

        token = token.substring("Bearer ".length()).trim(); // Remove o prefixo "Bearer "

        try {
            // Valida o token e extrai as claims
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(CHAVE)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Armazena o ID no contexto da requisição
            requestContext.setProperty("userId", claims.get("id", Integer.class));

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"mensagem\": \"Token expirado.\"}")
                    .build());
        } catch (io.jsonwebtoken.SignatureException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"mensagem\": \"Assinatura do token inválida.\"}")
                    .build());
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"mensagem\": \"Token inválido ou erro desconhecido.\"}")
                    .build());
        }
    }
}
