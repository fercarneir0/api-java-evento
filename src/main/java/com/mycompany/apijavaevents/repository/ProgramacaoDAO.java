package com.mycompany.apijavaevents.repository;

import com.mycompany.model.Programacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramacaoDAO {
    private final Connection connection;

    public ProgramacaoDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Programacao programacao) throws SQLException {
        String sql = "INSERT INTO programacao (nome, descricao, dataInicio, dataFim, nomePalestrante, miniCurriculo) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, programacao.getNome());
            stmt.setString(2, programacao.getDescricao());
            stmt.setString(3, programacao.getDataInicio());
            stmt.setString(4, programacao.getDataFim());
            stmt.setString(5, programacao.getNomePalestrante());
            stmt.setString(6, programacao.getMiniCurriculo());
            stmt.executeUpdate();
        }
    }

    public List<Programacao> listarTodos() throws SQLException {
        String sql = "SELECT * FROM programacao";
        List<Programacao> programacoes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Programacao programacao = new Programacao();
                programacao.setNome(rs.getString("nome"));
                programacao.setDescricao(rs.getString("descricao"));
                programacao.setDataInicio(rs.getString("dataInicio"));
                programacao.setDataFim(rs.getString("dataFim"));
                programacao.setNomePalestrante(rs.getString("nomePalestrante"));
                programacao.setMiniCurriculo(rs.getString("miniCurriculo"));
                programacoes.add(programacao);
            }
        }
        return programacoes;
    }

    public Programacao buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM programacao WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Programacao programacao = new Programacao();
                    programacao.setNome(rs.getString("nome"));
                    programacao.setDescricao(rs.getString("descricao"));
                    programacao.setDataInicio(rs.getString("dataInicio"));
                    programacao.setDataFim(rs.getString("dataFim"));
                    programacao.setNomePalestrante(rs.getString("nomePalestrante"));
                    programacao.setMiniCurriculo(rs.getString("miniCurriculo"));
                    return programacao;
                }
            }
        }
        return null;
    }

    public void deletarPorNome(String nome) throws SQLException {
        String sql = "DELETE FROM programacao WHERE nome = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
        }
    }
}
