package main.java.dao.implem;

import main.java.cinema.Filme;
import main.java.conexao.DBConn;
import main.java.dao.intefaces.FilmeDAO;
import main.java.conexao.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilmeDAOImpl implements FilmeDAO {
    private final DBConn conn;

    public FilmeDAOImpl(DBConn conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Filme> buscarPeloId(Integer id) {
        String sql = "SELECT * FROM Filme WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Filme filme = new Filme(rs.getInt("id"), rs.getString("nome"), rs.getLong("duracao_s"));
                return Optional.of(filme);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Filme> listar() {
        List<Filme> filmes = new ArrayList<>();
        String sql = "SELECT * FROM Filme";
        try (PreparedStatement stmt = conn.statement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Filme filme = new Filme(rs.getInt("id"), rs.getString("nome"), rs.getLong("duracao_s"));
                filmes.add(filme);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return filmes;
    }


    @Override
    public void deletarPeloId(Integer id) {
        String sql = "DELETE FROM Filme WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Filme filme) {
        String sql = "UPDATE Filme SET nome = ?, duracao_s = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setString(1, filme.getNome());
            stmt.setLong(2, filme.getDuracao_s());
            stmt.setInt(3, filme.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Filme> consulta(String sql) {
        List<Filme> filmes = new ArrayList<>();
        try (PreparedStatement stmt = conn.statement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Filme filme = new Filme(rs.getInt("id"), rs.getString("nome"), rs.getLong("duracao_s"));
                filmes.add(filme);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return filmes;
    }

    @Override
    public Integer insert(Filme filme) {
        String sql = "INSERT INTO Filme (nome, duracao_s) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.statement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, filme.getNome());
            stmt.setLong(2, filme.getDuracao_s());
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
}