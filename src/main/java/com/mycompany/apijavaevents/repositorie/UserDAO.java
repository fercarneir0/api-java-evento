package com.mycompany.apijavaevents.repositorie;

import com.mycompany.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public String salvarUsuario(User user) {
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

            return "Usuário salvo com sucesso";

        } catch (SQLException e) {
            return ("Erro ao salvar o usuário" + e.getMessage());
        }
    }

    public String alterarUsuario(User user) {
        String sql = "UPDATE usuario set nome = ?, telefone = , email = ?, senha = ? WHERE CPF = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getNome());
            statement.setString(2, user.getTelefone());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getSenha());

            statement.executeUpdate();

            return "Informacoes alteradas com sucesso";
        } catch (SQLException e) {
            return ("Erro ao alterar informacoes do usuário" + e.getMessage());
        }
    }

    public String removerUsuario(User user) {
        String sql = "DELETE FROM usuario where email = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getEmail());

            int rowAffected = statement.executeUpdate();

            if (rowAffected > 0) {
                return "Usuário removido com sucesso";
            } else {
                return "Usuário não encontrado";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao deletar usuário" + e.getMessage();
        }
    }

    public String promoverAdministrador(User user) {
        String sql = "UPDATE usuario set administrador = true WHERE email = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getEmail());

            int rowAffected = statement.executeUpdate();

            if (rowAffected > 0) {
                return "Usuário promovido a administrador";
            } else {
                return "Usuário não encontrado";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao promover o usuário a administrador " + e.getMessage();
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
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()){
                User user = new User();
                user.setCpf(rs.getString("cpf"));
            }
        }
    }
}
