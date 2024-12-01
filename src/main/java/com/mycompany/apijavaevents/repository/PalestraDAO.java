package com.mycompany.apijavaevents.repository;

import com.mycompany.model.Palestra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PalestraDAO {

    public boolean salvarPalestra(Palestra palestra) {
        String sql = "INSERT INTO palestra (id, nome, descricao, data, local, palestrante, titulo, numeroVagas, dataLimiteInscricao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, palestra.getId());
            statement.setString(2, palestra.getNome());
            statement.setString(3, palestra.getDescricao());
            statement.setDate(4, Date.valueOf(palestra.getData())); // Converte LocalDate para Date
            statement.setString(5, palestra.getLocal());
            statement.setString(6, palestra.getPalestrante());
            statement.setString(7, palestra.getTitulo());
            statement.setInt(8, palestra.getNumeroVagas());
            statement.setDate(9, Date.valueOf(palestra.getDataLimiteInscricao())); // Converte LocalDate para Date

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao salvar palestra: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarPalestra(Palestra palestra) {
        String sql = "UPDATE palestra SET nome = ?, descricao = ?, data = ?, local = ?, palestrante = ?, titulo = ?, " +
                     "numeroVagas = ?, dataLimiteInscricao = ? WHERE id = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, palestra.getNome());
            statement.setString(2, palestra.getDescricao());
            statement.setDate(3, Date.valueOf(palestra.getData())); // Converte LocalDate para Date
            statement.setString(4, palestra.getLocal());
            statement.setString(5, palestra.getPalestrante());
            statement.setString(6, palestra.getTitulo());
            statement.setInt(7, palestra.getNumeroVagas());
            statement.setDate(8, Date.valueOf(palestra.getDataLimiteInscricao())); // Converte LocalDate para Date
            statement.setString(9, palestra.getId());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar palestra: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarPalestra(String id) {
        String sql = "DELETE FROM palestra WHERE id = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar palestra: " + e.getMessage());
            return false;
        }
    }

    public Palestra buscarPalestraPorId(String id) {
        String sql = "SELECT * FROM palestra WHERE id = ?";

        Connection conn;
        PreparedStatement statement;

        try {
            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            statement.setString(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Palestra palestra = new Palestra();
                palestra.setId(result.getString("id"));
                palestra.setNome(result.getString("nome"));
                palestra.setDescricao(result.getString("descricao"));
                palestra.setData(result.getDate("data").toLocalDate()); // Converte Date para LocalDate
                palestra.setLocal(result.getString("local"));
                palestra.setPalestrante(result.getString("palestrante"));
                palestra.setTitulo(result.getString("titulo"));
                palestra.setNumeroVagas(result.getInt("numeroVagas"));
                palestra.setDataLimiteInscricao(result.getDate("dataLimiteInscricao").toLocalDate()); // Converte Date para LocalDate
                return palestra;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar palestra por ID: " + e.getMessage());
        }

        return null;
    }

    public List<Palestra> listarPalestras() {
        String sql = "SELECT * FROM palestra";
        List<Palestra> palestras = new ArrayList<>();

        Connection conn;
        PreparedStatement statement;

        try {
            conn = DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Palestra palestra = new Palestra();
                palestra.setId(result.getString("id"));
                palestra.setNome(result.getString("nome"));
                palestra.setDescricao(result.getString("descricao"));
                palestra.setData(result.getDate("data").toLocalDate()); // Converte Date para LocalDate
                palestra.setLocal(result.getString("local"));
                palestra.setPalestrante(result.getString("palestrante"));
                palestra.setTitulo(result.getString("titulo"));
                palestra.setNumeroVagas(result.getInt("numeroVagas"));
                palestra.setDataLimiteInscricao(result.getDate("dataLimiteInscricao").toLocalDate()); // Converte Date para LocalDate
                palestras.add(palestra);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar palestras: " + e.getMessage());
        }

        return palestras;
    }
}
