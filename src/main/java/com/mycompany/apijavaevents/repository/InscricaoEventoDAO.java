package com.mycompany.apijavaevents.repository;

import com.mycompany.model.InscricaoEvento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class InscricaoEventoDAO {

    public boolean inscreverUsuarioEvento(InscricaoEvento inscricao) {
        String sql = "INSERT INTO inscricoes_eventos (evento_id, usuario_id, data_inscricao) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, inscricao.getEventoId());
            statement.setInt(2, inscricao.getUserId());
            statement.setDate(3, (java.sql.Date) inscricao.getDataInscricao());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Retorna true se a inserção foi bem-sucedida
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inscrever usuário no evento", e);
        }
    }

    // Método para verificar se o usuário já está inscrito no evento
    public boolean verificarInscricao(int usuarioId, int eventoId) {
        String sql = "SELECT COUNT(*) FROM inscricoes_eventos WHERE evento_id = ? AND usuario_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, eventoId);
            statement.setInt(2, usuarioId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Retorna true se já existir a inscrição
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar inscrição no evento", e);
        }
    }

    // Método para obter a data limite de inscrições de um evento
    public Date obterDataLimiteEvento(int eventoId) {
        String sql = "SELECT data_limite_inscricao FROM eventos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, eventoId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getTimestamp("data_limite_inscricao");
            } else {
                throw new IllegalArgumentException("Evento não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter data limite do evento", e);
        }
    }

    public List<InscricaoEvento> listarInscricoesEvento(int eventoId) {
        String sql = """
                    SELECT 
                        e.id AS id_evento,
                        u.cpf AS cpf,
                        u.nome AS nome,
                        u.email AS email,
                        u.telefone AS telefone
                        FROM inscricoes_eventos ie
                        INNER JOIN usuario u ON ie.usuario_id = u.id
                        INNER JOIN eventos e ON ie.evento_id = e.id
                        WHERE e.id = ?""";
        List<InscricaoEvento> inscricaoEvento = new ArrayList<>();

        Connection conn;

        PreparedStatement statement;

        try {
            conn = (Connection) DatabaseConnection.getConnection();

            statement = conn.prepareStatement(sql);
            statement.setInt(1, eventoId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                InscricaoEvento inscricao = new InscricaoEvento();
                inscricao.setEventoId(result.getInt("id_evento"));
                inscricao.setCpfParticipante(result.getString("cpf"));
                inscricao.setNomeParticipante(result.getString("nome"));
                inscricao.setEmailParticipante(result.getString("email"));
                inscricao.setTelefoneParticipante(result.getString("telefone"));

                inscricaoEvento.add(inscricao);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Erro ao listar as inscricoes");
        }

        return inscricaoEvento;
    }

    public Date obterDataInicioEvento(int eventoId) {
        String sql = "SELECT data_inicio FROM eventos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, eventoId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getTimestamp("data_inicio");
            } else {
                throw new IllegalArgumentException("Evento não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter a data de início do evento", e);
        }
    }

    public boolean removerInscricao(int usuarioId, int eventoId) {
        String sql = "DELETE FROM inscricoes_eventos WHERE evento_id = ? AND usuario_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, eventoId);
            statement.setInt(2, usuarioId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Retorna true se a remoção foi bem-sucedida
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover inscrição no evento", e);
        }
    }

    public int obterNumeroVagasRestantes(int eventoId) {
        String sql = """
        SELECT (e.numero_vagas - COUNT(ie.id)) AS vagas_restantes
        FROM eventos e
        LEFT JOIN inscricoes_eventos ie ON e.id = ie.evento_id
        WHERE e.id = ?
        GROUP BY e.numero_vagas
                                """;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, eventoId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("vagas_restantes");
            } else {
                throw new IllegalArgumentException("Evento não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter número de vagas restantes", e);
        }
    }

    public List<InscricaoEvento> listarInscritosPorEvento(int eventoId) {
        String sql = """
        SELECT u.id AS usuario_id, u.nome, u.cpf, u.email, e.id AS evento_id, e.nome AS nome_evento
        FROM inscricoes_eventos ie
        INNER JOIN usuario u ON ie.usuario_id = u.id
        INNER JOIN eventos e ON ie.evento_id = e.id
        WHERE e.id = ?
    """;

        List<InscricaoEvento> inscritos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, eventoId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                InscricaoEvento inscricao = new InscricaoEvento();
                inscricao.setEventoId(resultSet.getInt("evento_id"));
                inscricao.setUserId(resultSet.getInt("usuario_id"));
                inscricao.setNomeParticipante(resultSet.getString("nome"));
                inscricao.setCpfParticipante(resultSet.getString("cpf"));
                inscricao.setEmailParticipante(resultSet.getString("email"));
                inscritos.add(inscricao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar inscritos do evento", e);
        }
        return inscritos;
    }
}
