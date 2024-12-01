package com.mycompany.apijavaevents.repository;

import com.mycompany.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    public boolean salvarUsuario(User user) {

        String sql = "INSERT INTO usuario (usuario_id, nome, telefone, cpf, email, senha, administrador) VALUES (nextval('usuario_usuario_id_seq'),?,?,?,?,?,?)";

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
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean alterarUsuario(User user) {

        String sql = "UPDATE usuario SET nome = ?, telefone = ?, email = ?, senha = ? WHERE usuario_id = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getNome());
            statement.setString(2, user.getTelefone());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getSenha());
            statement.setInt(5, user.getId());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Optional<User> buscarUsuarioPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, email);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                // Mapeia o resultado para o objeto User
                User user = new User();
                user.setId(result.getInt("usuario_id"));
                user.setNome(result.getString("nome"));
                user.setEmail(result.getString("email"));
                user.setTelefone(result.getString("telefone"));
                user.setSenha(result.getString("senha"));

                return Optional.of(user); // Retorna o usuário encontrado
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty(); // Retorna vazio se não encontrar o usuário
    }

public boolean removerUsuario(int id) {
        
        String sql = "DELETE FROM usuario WHERE usuario_id = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setInt(1, id);

            int rowAffected = statement.executeUpdate();

            return rowAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean promoverAdministrador(int id) {
     
        String sql = "UPDATE usuario SET administrador = true WHERE usuario_id = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setInt(1, id);

            int rowAffected = statement.executeUpdate();

            return rowAffected > 0;
        } catch (SQLException e){
            e.getMessage();
            return false;
        }   
    }

    public List<User> listarUsuarios() {
        String sql = "SELECT usuario_id, cpf, nome, email, administrador from usuario ORDER BY usuario_id";
        List<User> users = new ArrayList<>();

        Connection conn;

        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                User user = new User();
                user.setId(result.getInt("usuario_id"));
                user.setCpf(result.getString("cpf"));
                user.setNome(result.getString("nome"));
                user.setEmail(result.getString("email"));
                user.setAdmin(result.getBoolean("administrador"));

                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Erro ao listar os usuários");
        }

        return users;
    }

    public boolean isAdmin(int id) {
        String sql = "SELECT administrador from usuario WHERE usuario_id = ?";

        Connection conn;

        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setInt(1, id);

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
                return true; // Retorna true se encontrar o email
            }
        } catch (SQLException e){
            throw new RuntimeException("Erro ao verificar email", e);
        }
        return false; //Retorna true se não encontrar o email
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
                return true; // Retorna true se encontrar o CPF
            }
        } catch (SQLException e){
            throw new RuntimeException("Erro ao verificar CPF", e);
        }
        return false; // Retorna falso se não encontrar o CPF;
    }
}
