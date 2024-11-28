package com.mycompany.apijavaevents.repository;

import com.mycompany.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean salvarUsuario(User user) {
        
        if(!verificarEmail(user) || !verificarCPF(user)){
            return false; // Retorna falso se existir esse email e esse CPF
        }
        
        String sql = "INSERT INTO usuario (nome, telefone, cpf, email, senha, administrador) VALUES (?,?,?,?,?,?)";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);
            

            statement.setString(1, user.getNome());
            statement.setString(2, user.getTelefone());
            statement.setString(3, user.getCpf());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getSenha());
            statement.setBoolean(6, user.isAdmin());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterarUsuario(User user) {
        
        if(!isAdmin(user)){
            return false; //Retorna falso se o usuário não for administrador
        }
        
        String sql = "UPDATE usuario SET nome = ?, telefone = ?, email = ?, senha = ? WHERE cpf = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getNome());
            statement.setString(2, user.getTelefone());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getSenha());
            statement.setString(5, user.getCpf());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removerUsuario(String email, User user) {
        if(!isAdmin(user)){
            return false; //Retorna falso se o usuário não for administrador
        }
        
        String sql = "DELETE FROM usuario WHERE email = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, email);

            int rowAffected = statement.executeUpdate();

            return rowAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean promoverAdministrador(String email, User user) {
        if(!isAdmin(user)){
            return false; //Retorna falso se o usuário não for administrador
        }
        String sql = "UPDATE usuario SET administrador = true WHERE email = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, email);

            int rowAffected = statement.executeUpdate();

            return rowAffected > 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }   
    }

    public List<User> listarUsuarios() {
        String sql = "SELECT cpf, nome, email, administrador from usuario";
        List<User> users = new ArrayList<>();

        Connection conn;

        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                User user = new User();
                user.setCpf(result.getString("cpf"));
                user.setNome(result.getString("nome"));
                user.setEmail(result.getString("email"));
                user.setAdmin(result.getBoolean("administrador"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar os usuários");
        }

        return users;
    }

    public boolean isAdmin(User user) {
        String sql = "SELECT administrador from usuario WHERE email = ?";

        Connection conn;

        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getEmail());

            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                return result.getBoolean("administrador");
            } 
        } catch (SQLException e){
            throw new RuntimeException ("Erro ao verificar se o usuário é administrador" + e.getMessage());
        }
        return false;
    }
    
    public boolean verificarEmail(User user) {
        String sql = "SELECT * from usuario WHERE email = ?";

        Connection conn;

        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getEmail());

            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                return false; // Retorna false se não encontrar o email
            }
        } catch (SQLException e){
            throw new RuntimeException("Erro ao verificar email", e);
        }
        return true; //Retorna true se encontrar o email
    }
    
    public boolean verificarCPF(User user){
        String sql = "SELECT * from usuario WHERE cpf = ?";

        Connection conn;

        PreparedStatement statement;
        
        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getCpf());

            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                return false; // Retorna falso se não encontrar o CPF
            }
        } catch (SQLException e){
            throw new RuntimeException("Erro ao verificar CPF", e);
        }
        return true; // Retorna true caso encontre o CPF;
    }
}
