package com.mycompany.apijavaevents.repository;

import com.mycompany.model.Minicurso;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MinicursoDAO {

    public boolean salvarMinicurso(Minicurso minicurso) {
        String sql = "INSERT INTO minicurso (id, nome, data, local, descricao, nomeInstrutor, cpfInstrutor, emailInstrutor, numeroVagas, dataLimiteInscricao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, minicurso.getId());
            statement.setString(2, minicurso.getNome());
            statement.setDate(3, Date.valueOf(minicurso.getData())); // Converte LocalDate para Date
            statement.setString(4, minicurso.getLocal());
            statement.setString(5, minicurso.getDescricao());
            statement.setString(6, minicurso.getNomeInstrutor());
            statement.setString(7, minicurso.getCpfInstrutor());
            statement.setString(8, minicurso.getEmailInstrutor());
            statement.setInt(9, minicurso.getNumeroVagas());
            statement.setDate(10, Date.valueOf(minicurso.getDataLimiteInscricao())); // Converte LocalDate para Date

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao salvar minicurso: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarMinicurso(Minicurso minicurso) {
        String sql = "UPDATE minicurso SET nome = ?, data = ?, local = ?, descricao = ?, nomeInstrutor = ?, cpfInstrutor = ?, " +
                     "emailInstrutor = ?, numeroVagas = ?, dataLimiteInscricao = ? WHERE id = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, minicurso.getNome());
            statement.setDate(2, Date.valueOf(minicurso.getData())); // Converte LocalDate para Date
            statement.setString(3, minicurso.getLocal());
            statement.setString(4, minicurso.getDescricao());
            statement.setString(5, minicurso.getNomeInstrutor());
            statement.setString(6, minicurso.getCpfInstrutor());
            statement.setString(7, minicurso.getEmailInstrutor());
            statement.setInt(8, minicurso.getNumeroVagas());
            statement.setDate(9, Date.valueOf(minicurso.getDataLimiteInscricao())); // Converte LocalDate para Date
            statement.setString(10, minicurso.getId());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar minicurso: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarMinicurso(String id) {
        String sql = "DELETE FROM minicurso WHERE id = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar minicurso: " + e.getMessage());
            return false;
        }
    }

    public Minicurso buscarMinicursoPorId(String id) {
        String sql = "SELECT * FROM minicurso WHERE id = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Minicurso minicurso = new Minicurso();
                minicurso.setId(result.getString("id"));
                minicurso.setNome(result.getString("nome"));
                minicurso.setData(result.getDate("data").toLocalDate()); // Converte Date para LocalDate
                minicurso.setLocal(result.getString("local"));
                minicurso.setDescricao(result.getString("descricao"));
                minicurso.setNomeInstrutor(result.getString("nomeInstrutor"));
                minicurso.setCpfInstrutor(result.getString("cpfInstrutor"));
                minicurso.setEmailInstrutor(result.getString("emailInstrutor"));
                minicurso.setNumeroVagas(result.getInt("numeroVagas"));
                minicurso.setDataLimiteInscricao(result.getDate("dataLimiteInscricao").toLocalDate()); // Converte Date para LocalDate
                return minicurso;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar minicurso por ID: " + e.getMessage());
        }

        return null;
    }

    public List<Minicurso> listarMinicursos() {
        String sql = "SELECT * FROM minicurso";
        List<Minicurso> minicursos = new ArrayList<>();

        Connection conn;
        PreparedStatement statement;

        try {
            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Minicurso minicurso = new Minicurso();
                minicurso.setId(result.getString("id"));
                minicurso.setNome(result.getString("nome"));
                minicurso.setData(result.getDate("data").toLocalDate()); // Converte Date para LocalDate
                minicurso.setLocal(result.getString("local"));
                minicurso.setDescricao(result.getString("descricao"));
                minicurso.setNomeInstrutor(result.getString("nomeInstrutor"));
                minicurso.setCpfInstrutor(result.getString("cpfInstrutor"));
                minicurso.setEmailInstrutor(result.getString("emailInstrutor"));
                minicurso.setNumeroVagas(result.getInt("numeroVagas"));
                minicurso.setDataLimiteInscricao(result.getDate("dataLimiteInscricao").toLocalDate()); // Converte Date para LocalDate
                minicursos.add(minicurso);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar minicursos: " + e.getMessage());
        }

        return minicursos;
    }
}
