package main.java.dao.implem;

import main.java.cinema.Sessao;
import main.java.cinema.Sala;
import main.java.cinema.Filme;
import main.java.conexao.DBConn;
import main.java.dao.intefaces.SessaoDAO;
import main.java.conexao.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SessaoDAOImpl implements SessaoDAO {
    private final DBConn conn;

    public SessaoDAOImpl(DBConn conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Sessao> buscarPeloId(Integer id) {
        String sql = "SELECT s.id, s.dataHora, sa.id as sala_id, sa.nome as sala_nome, sa.capacidade as sala_capacidade, " +
                "f.id as filme_id, f.nome as filme_nome, f.duracao_s as filme_duracao_s " +
                "FROM Sessao s " +
                "JOIN Sala sa ON s.sala_id = sa.id " +
                "JOIN Filme f ON s.filme_id = f.id " +
                "WHERE s.id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Sala sala = new Sala(rs.getInt("sala_id"), rs.getString("sala_nome"), rs.getInt("sala_capacidade"), rs.getInt("cinema_id"));
                Filme filme = new Filme(rs.getInt("filme_id"), rs.getString("filme_nome"), rs.getLong("filme_duracao_s"));
                Sessao sessao = new Sessao(rs.getInt("id"), sala, filme, rs.getTimestamp("dataHora").toLocalDateTime());
                return Optional.of(sessao);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Sessao> listar() {
        List<Sessao> sessoes = new ArrayList<>();
        String sql = "SELECT s.id, s.dataHora, sa.id as sala_id, sa.nome as sala_nome, sa.capacidade as sala_capacidade, " +
                "sa.cinema_id, f.id as filme_id, f.nome as filme_nome, f.duracao_s as filme_duracao_s " +
                "FROM Sessao s " +
                "JOIN Sala sa ON s.sala_id = sa.id " +
                "JOIN Filme f ON s.filme_id = f.id";
        try (PreparedStatement stmt = conn.statement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Sala sala = new Sala(rs.getInt("sala_id"), rs.getString("sala_nome"), rs.getInt("sala_capacidade"), rs.getInt("cinema_id"));
                Filme filme = new Filme(rs.getInt("filme_id"), rs.getString("filme_nome"), rs.getLong("filme_duracao_s"));
                Sessao sessao = new Sessao(rs.getInt("id"), sala, filme, rs.getTimestamp("dataHora").toLocalDateTime());
                sessoes.add(sessao);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return sessoes;
    }

    @Override
    public Integer insert(Sessao sessao) {
        String sql = "INSERT INTO Sessao (sala_id, filme_id, dataHora) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.statement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, sessao.getSala().getId());
            stmt.setInt(2, sessao.getFilme().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(sessao.getDataHora()));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return null;
    }

    @Override
    public void deletarPeloId(Integer id) {
        String sql = "DELETE FROM Sessao WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Sessao sessao) {
        String sql = "UPDATE Sessao SET sala_id = ?, filme_id = ?, dataHora = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, sessao.getSala().getId());
            stmt.setInt(2, sessao.getFilme().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(sessao.getDataHora()));
            stmt.setInt(4, sessao.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Sessao> consulta(String sql) {
        List<Sessao> sessoes = new ArrayList<>();
        try (PreparedStatement stmt = conn.statement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Sala sala = new Sala(rs.getInt("sala_id"), rs.getString("sala_nome"), rs.getInt("sala_capacidade"), rs.getInt("cinema_id"));
                Filme filme = new Filme(rs.getInt("filme_id"), rs.getString("filme_nome"), rs.getLong("filme_duracao_s"));
                Sessao sessao = new Sessao(rs.getInt("id"), sala, filme, rs.getTimestamp("dataHora").toLocalDateTime());
                sessoes.add(sessao);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return sessoes;
    }
}