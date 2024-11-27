package com.mycompany.apijavaevents.repositorie;

import com.mycompany.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean salvarUsuario(User user) {
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
        String sql = "UPDATE usuario set nome = ?, telefone = ?, email = ?, senha = ? WHERE CPF = ?";

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

    public boolean removerUsuario(String email) {
        String sql = "DELETE FROM usuario where email = ?";

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

    public boolean promoverAdministrador(String email) {
        String sql = "UPDATE usuario set administrador = true WHERE email = ?";

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

    public static boolean isAdmin(User user) {
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
        String sql = "SELECT from usuario WHERE email = ?";

        Connection conn;

        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getEmail());

            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                System.out.println("Já existe um usuário com esse email");
            }
        } catch (SQLException e){
            System.out.println("Email não encontrado" + e.getMessage());
        }
        return true;
    }
    
    public boolean verificarCPF(User user){
        String sql = "SELECT from usuario WHERE cpf = ?";

        Connection conn;

        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getCpf());

            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                System.out.println("Já existe um usuário com esse CPF");
            }
        } catch (SQLException e){
            System.out.println("Não existe um usuário com esse CPF" + e.getMessage());
        } 
        return true;
    }
}
