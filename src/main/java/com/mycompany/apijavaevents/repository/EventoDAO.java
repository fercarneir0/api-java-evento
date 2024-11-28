package com.mycompany.apijavaevents.repository;


import com.mycompany.model.Evento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {
    private final Connection connection;

    public EventoDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Evento evento) throws SQLException {
        String sql = "INSERT INTO evento (id, nome, dataInicio, dataFim, local, descricao, numeroVagas, dataLimiteInscricao, cpfResponsavel, emailResponsavel) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, evento.getId());
            stmt.setString(2, evento.getNome());
            stmt.setString(3, evento.getDataInicio());
            stmt.setString(4, evento.getDataFim());
            stmt.setString(5, evento.getLocal());
            stmt.setString(6, evento.getDescricao());
            stmt.setInt(7, evento.getNumeroVagas());
            stmt.setString(8, evento.getDataLimiteInscricao());
            stmt.setString(9, evento.getCpfResponsavel());
            stmt.setString(10, evento.getEmailResponsavel());
            stmt.executeUpdate();
        }
    }

    public List<Evento> listarTodos() throws SQLException {
        String sql = "SELECT * FROM evento";
        List<Evento> eventos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getString("id"));
                evento.setNome(rs.getString("nome"));
                evento.setDataInicio(rs.getString("dataInicio"));
                evento.setDataFim(rs.getString("dataFim"));
                evento.setLocal(rs.getString("local"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setNumeroVagas(rs.getInt("numeroVagas"));
                evento.setDataLimiteInscricao(rs.getString("dataLimiteInscricao"));
                evento.setCpfResponsavel(rs.getString("cpfResponsavel"));
                evento.setEmailResponsavel(rs.getString("emailResponsavel"));
                eventos.add(evento);
            }
        }
        return eventos;
    }

    public Evento buscarPorId(String id) throws SQLException {
        String sql = "SELECT * FROM evento WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Evento evento = new Evento();
                    evento.setId(rs.getString("id"));
                    evento.setNome(rs.getString("nome"));
                    evento.setDataInicio(rs.getString("dataInicio"));
                    evento.setDataFim(rs.getString("dataFim"));
                    evento.setLocal(rs.getString("local"));
                    evento.setDescricao(rs.getString("descricao"));
                    evento.setNumeroVagas(rs.getInt("numeroVagas"));
                    evento.setDataLimiteInscricao(rs.getString("dataLimiteInscricao"));
                    evento.setCpfResponsavel(rs.getString("cpfResponsavel"));
                    evento.setEmailResponsavel(rs.getString("emailResponsavel"));
                    return evento;
                }
            }
        }
        return null;
    }

    public void deletarPorId(String id) throws SQLException {
        String sql = "DELETE FROM evento WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
}
