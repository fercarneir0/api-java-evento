package com.mycompany.apijavaevents.repository;

import com.mycompany.model.Participante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDAO {
    private final Connection connection;

    public ParticipanteDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Participante participante) throws SQLException {
        String sql = "INSERT INTO participante (nome, cpf, email, telefone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getCpf());
            stmt.setString(3, participante.getEmail());
            stmt.setString(4, participante.getTelefone());
            stmt.executeUpdate();
        }
    }

    public List<Participante> listarTodos() throws SQLException {
        String sql = "SELECT * FROM participante";
        List<Participante> participantes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Participante participante = new Participante();
                participante.setNome(rs.getString("nome"));
                participante.setCpf(rs.getString("cpf"));
                participante.setEmail(rs.getString("email"));
                participante.setTelefone(rs.getString("telefone"));
                participantes.add(participante);
            }
        }
        return participantes;
    }

    public Participante buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM participante WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Participante participante = new Participante();
                    participante.setNome(rs.getString("nome"));
                    participante.setCpf(rs.getString("cpf"));
                    participante.setEmail(rs.getString("email"));
                    participante.setTelefone(rs.getString("telefone"));
                    return participante;
                }
            }
        }
        return null;
    }

    public void deletarPorCpf(String cpf) throws SQLException {
        String sql = "DELETE FROM participante WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }
}
