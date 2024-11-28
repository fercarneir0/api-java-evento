package com.mycompany.controller;

import com.mycompany.apijavaevents.repository.UserDAO;
import com.mycompany.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.SecretKey;

public class UserBC {

    private UserDAO userDAO;
    private final static SecretKey CHAVE = Keys.hmacShaKeyFor(System.getenv("CHAVE")
            .getBytes(StandardCharsets.UTF_8));

    public UserBC() {
        this.userDAO = new UserDAO();
    }

    public String TokenValidator(String token) throws Exception {
        try {
            String email = Jwts.parserBuilder()
                    .setSigningKey(CHAVE)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return email;
        } catch (Exception e) {
            throw new Exception("Não foi possivel validar o token" + e.getMessage());
        }
    }

    public static Claims parseToken(String token) throws Exception {
        if (token.isEmpty()) {
            throw new Exception("Token vazio");
        }
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(CHAVE)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new Exception("Erro: " + e.getMessage());
        }
    }
    public boolean salvarUsuario(User user){
        if(!userDAO.verificarEmail(user) || !userDAO.verificarCPF(user)){
            return userDAO.salvarUsuario(user);
        }
        return false; // Retorna falso se o usuário não foi administrador
    }
    
    public boolean alterarUsuario(User user){
      if(isAdmin(user)){
            return userDAO.alterarUsuario(user);
        }
        return false; // Retorna falso se o usuário não foi administrador
    }
    
    public boolean removerUsuario(String email, User user){
        if(isAdmin(user)){
            return userDAO.removerUsuario(email, user);
        }
        return false; // Retorna falso se o usuário não foi administrador
    }
    
    public boolean promoverAdministrador(String email, User user){
       if(isAdmin(user)){
            return userDAO.promoverAdministrador(email, user);
        }
        return false; // Retorna falso se o usuário não foi administrador
    }
    
    public List<User> listarUsuarios(){
        return userDAO.listarUsuarios();
    }
    
    public boolean isAdmin(User user){
      return userDAO.isAdmin(user);
    }
}
