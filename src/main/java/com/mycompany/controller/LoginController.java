package com.mycompany.controller;

import com.mycompany.apijavaevents.repository.UserDAO;
import com.mycompany.model.Login;
import com.mycompany.model.User;
import java.util.Optional;

public class LoginController {

    public User autenticarUsuario(Login login) {
        UserDAO userDAO = new UserDAO();

        System.out.println("Email recebido: " + login.getEmail());
        System.out.println("Senha recebida: " + login.getSenha());

        Optional<User> usuarioOpt = userDAO.buscarUsuarioPorEmail(login.getEmail());

        if (usuarioOpt.isPresent()) {
            User user = usuarioOpt.get();
            
            System.out.println("Usuário encontrado: " + user.getEmail());

            if(login.getSenha().equals(user.getSenha())){
                return user; // Retorna o usuário autenticado
            }
        }

        System.out.println("Usuário não encontrado.");
        return null; // Retorna nulo se o usuário não for encontrado
    }
}
