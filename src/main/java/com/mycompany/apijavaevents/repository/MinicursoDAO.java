package com.mycompany.apijavaevents.repository;

import com.mycompany.model.Minicurso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MinicursoDAO {
    private final Connection connection;

    public MinicursoDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Minicurso minicurso) throws SQLException {
        String sql = "INSERT INTO minicurso (id, nome, data, local, descricao, nomeInstrutor, cpfInstrutor, emailInstrutor, numeroVagas, dataLimiteInscricao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, minicurso.getId());
            stmt.setString(2, minicurso.getNome());
            stmt.setTimestamp(3, Timestamp.valueOf(minicurso.getData()));
            stmt.setString(4, minicurso.getLocal());
            stmt.setString(5, minicurso.getDescricao());
            stmt.setString(6, minicurso.getNomeInstrutor());
            stmt.setString(7, minicurso.getCpfInstrutor());
            stmt.setString(8, minicurso.getEmailInstrutor());
            stmt.setInt(9, minicurso.getNumeroVagas());
            stmt.setTimestamp(10, Timestamp.valueOf(minicurso.getDataLimiteInscricao()));
            stmt.executeUpdate();
        }
    }

    public List<Minicurso> listarTodos() throws SQLException {
        String sql = "SELECT * FROM minicurso";
        List<Minicurso> minicursos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Minicurso minicurso = new Minicurso();
                minicurso.setId(rs.getString("id"));
                minicurso.setNome(rs.getString("nome"));
                minicurso.setData(rs.getTimestamp("data").toLocalDateTime().toString());
                minicurso.setLocal(rs.getString("local"));
                minicurso.setDescricao(rs.getString("descricao"));
                minicurso.setNomeInstrutor(rs.getString("nomeInstrutor"));
                minicurso.setCpfInstrutor(rs.getString("cpfInstrutor"));
                minicurso.setEmailInstrutor(rs.getString("emailInstrutor"));
                minicurso.setNumeroVagas(rs.getInt("numeroVagas"));
                minicurso.setDataLimiteInscricao(rs.getTimestamp("dataLimiteInscricao").toLocalDateTime().toString());
                minicursos.add(minicurso);
            }
        }
        return minicursos;
    }

    public Minicurso buscarPorId(String id) throws SQLException {
        String sql = "SELECT * FROM minicurso WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Minicurso minicurso = new Minicurso();
                    minicurso.setId(rs.getString("id"));
                    minicurso.setNome(rs.getString("nome"));
                    minicurso.setData(rs.getTimestamp("data").toLocalDateTime().toString());
                    minicurso.setLocal(rs.getString("local"));
                    minicurso.setDescricao(rs.getString("descricao"));
                    minicurso.setNomeInstrutor(rs.getString("nomeInstrutor"));
                    minicurso.setCpfInstrutor(rs.getString("cpfInstrutor"));
                    minicurso.setEmailInstrutor(rs.getString("emailInstrutor"));
                    minicurso.setNumeroVagas(rs.getInt("numeroVagas"));
                    minicurso.setDataLimiteInscricao(rs.getTimestamp("dataLimiteInscricao").toLocalDateTime().toString());
                    return minicurso;
                }
            }
        }
        return null;
    }

    public void atualizar(Minicurso minicurso) throws SQLException {
        String sql = "UPDATE minicurso SET nome = ?, data = ?, local = ?, descricao = ?, nomeInstrutor = ?, cpfInstrutor = ?, " +
                     "emailInstrutor = ?, numeroVagas = ?, dataLimiteInscricao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, minicurso.getNome());
            stmt.setTimestamp(2, Timestamp.valueOf(minicurso.getData()));
            stmt.setString(3, minicurso.getLocal());
            stmt.setString(4, minicurso.getDescricao());
            stmt.setString(5, minicurso.getNomeInstrutor());
            stmt.setString(6, minicurso.getCpfInstrutor());
            stmt.setString(7, minicurso.getEmailInstrutor());
            stmt.setInt(8, minicurso.getNumeroVagas());
            stmt.setTimestamp(9, Timestamp.valueOf(minicurso.getDataLimiteInscricao()));
            stmt.setString(10, minicurso.getId());
            stmt.executeUpdate();
        }
    }

    public void deletarPorId(String id) throws SQLException {
        String sql = "DELETE FROM minicurso WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
}
