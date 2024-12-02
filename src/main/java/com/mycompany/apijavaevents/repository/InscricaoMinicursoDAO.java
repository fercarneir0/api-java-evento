package com.mycompany.apijavaevents.repository;

import com.mycompany.controller.InscricaoMinicursoController;
import com.mycompany.model.InscricaoMinicurso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InscricaoMinicursoDAO {

    private InscricaoMinicursoController controller;

    public boolean inscreverUsuarioMinicurso(InscricaoMinicurso inscricao) {
        String sql = "INSERT INTO inscricoes_minicursos (minicurso_id, usuario_id, data_inscricao) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, inscricao.getMinicursoId());
            statement.setInt(2, inscricao.getUserId());
            statement.setDate(3, new java.sql.Date(inscricao.getDataInscricao().getTime()));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inscrever usuário no minicurso", e);
        }
    }

    public int obterNumeroVagasRestantes(int minicursoId) {
        String sql = """
        SELECT (m.numero_vagas - COUNT(im.id)) AS vagas_restantes
        FROM minicursos m
        LEFT JOIN inscricoes_minicursos im ON m.id = im.minicurso_id
        WHERE m.id = ?
        GROUP BY m.numero_vagas
    """;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, minicursoId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("vagas_restantes");
            } else {
                throw new IllegalArgumentException("Minicurso não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter número de vagas restantes", e);
        }
    }

    public Date[] obterPeriodoMinicurso(int minicursoId) {
        String sql = "SELECT data_inicio, hora_inicio, data_fim, hora_fim FROM minicursos WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, minicursoId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Date dataInicio = new Date(resultSet.getDate("data_inicio").getTime());
                Date horaInicio = new Date(resultSet.getTime("hora_inicio").getTime());
                Date dataFim = new Date(resultSet.getDate("data_fim").getTime());
                Date horaFim = new Date(resultSet.getTime("hora_fim").getTime());

                return new Date[]{dataInicio, horaInicio, dataFim, horaFim};
            } else {
                throw new IllegalArgumentException("Minicurso não encontrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter período do minicurso", e);
        }
    }

    public List<InscricaoMinicurso> listarInscritosPorMinicurso(int minicursoId) {
        String sql = """
        SELECT u.id AS usuario_id, u.nome, u.cpf, u.email, m.id AS minicurso_id, m.nome AS nome_minicurso
        FROM inscricoes_minicursos im
        INNER JOIN usuario u ON im.usuario_id = u.id
        INNER JOIN minicursos m ON im.minicurso_id = m.id
        WHERE m.id = ?
    """;

        List<InscricaoMinicurso> inscritos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, minicursoId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                InscricaoMinicurso inscricao = new InscricaoMinicurso();
                inscricao.setMinicursoId(resultSet.getInt("minicurso_id"));
                inscricao.setUserId(resultSet.getInt("usuario_id"));
                inscricao.setNomeParticipante(resultSet.getString("nome"));
                inscricao.setCpfParticipante(resultSet.getString("cpf"));
                inscricao.setEmailParticipante(resultSet.getString("email"));
                inscritos.add(inscricao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar inscritos no minicurso", e);
        }

        return inscritos;
    }

    public boolean removerInscricao(int usuarioId, int minicursoId) {
        String sql = "DELETE FROM inscricoes_minicursos WHERE minicurso_id = ? AND usuario_id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, minicursoId);
            statement.setInt(2, usuarioId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Retorna true se a remoção foi bem-sucedida
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover inscrição no minicurso", e);
        }
    }
}
