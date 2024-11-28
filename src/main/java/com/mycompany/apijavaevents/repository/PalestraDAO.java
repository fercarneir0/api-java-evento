package com.mycompany.apijavaevents.repository;

import com.mycompany.model.Palestra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PalestraDAO {
    private final Connection connection;

    public PalestraDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Palestra palestra) throws SQLException {
        String sql = "INSERT INTO palestra (id, titulo, palestrante, data, local, descricao, numeroVagas, dataLimiteInscricao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, palestra.getId());
            stmt.setString(2, palestra.getTitulo());
            stmt.setString(3, palestra.getPalestrante());
            stmt.setTimestamp(4, Timestamp.valueOf(palestra.getData()));
            stmt.setString(5, palestra.getLocal());
            stmt.setString(6, palestra.getDescricao());
            stmt.setInt(7, palestra.getNumeroVagas());
            stmt.setTimestamp(8, Timestamp.valueOf(palestra.getDataLimiteInscricao()));
            stmt.executeUpdate();
        }
    }

    public List<Palestra> listarTodas() throws SQLException {
        String sql = "SELECT * FROM palestra";
        List<Palestra> palestras = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Palestra palestra = new Palestra();
                palestra.setId(rs.getString("id"));
                palestra.setTitulo(rs.getString("titulo"));
                palestra.setPalestrante(rs.getString("palestrante"));
                palestra.setData(rs.getTimestamp("data").toLocalDateTime().toString());
                palestra.setLocal(rs.getString("local"));
                palestra.setDescricao(rs.getString("descricao"));
                palestra.setNumeroVagas(rs.getInt("numeroVagas"));
                palestra.setDataLimiteInscricao(rs.getTimestamp("dataLimiteInscricao").toLocalDateTime().toString());
                palestras.add(palestra);
            }
        }
        return palestras;
    }

    public Palestra buscarPorId(String id) throws SQLException {
        String sql = "SELECT * FROM palestra WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Palestra palestra = new Palestra();
                    palestra.setId(rs.getString("id"));
                    palestra.setTitulo(rs.getString("titulo"));
                    palestra.setPalestrante(rs.getString("palestrante"));
                    palestra.setData(rs.getTimestamp("data").toLocalDateTime().toString());
                    palestra.setLocal(rs.getString("local"));
                    palestra.setDescricao(rs.getString("descricao"));
                    palestra.setNumeroVagas(rs.getInt("numeroVagas"));
                    palestra.setDataLimiteInscricao(rs.getTimestamp("dataLimiteInscricao").toLocalDateTime().toString());
                    return palestra;
                }
            }
        }
        return null;
    }

    public void deletarPorId(String id) throws SQLException {
        String sql = "DELETE FROM palestra WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
}
