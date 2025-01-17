package main.java.dao.implem;

import main.java.cinema.Sala;
import main.java.conexao.DBConn;
import main.java.dao.intefaces.SalaDAO;
import main.java.conexao.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalaDAOImpl implements SalaDAO {
    private final DBConn conn;

    public SalaDAOImpl(DBConn conn) {
        this.conn = conn;
    }

    @Override
    public Optional<Sala> buscarPeloId(Integer id) {
        String sql = "SELECT * FROM Sala WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Sala sala = new Sala(rs.getInt("id"), rs.getString("nome"), rs.getInt("capacidade"), rs.getInt("cinema_id"));
                return Optional.of(sala);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Sala> listar() {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT * FROM Sala";
        try (PreparedStatement stmt = conn.statement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Sala sala = new Sala(rs.getInt("id"), rs.getString("nome"), rs.getInt("capacidade"), rs.getInt("cinema_id"));
                salas.add(sala);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return salas;
    }

    @Override
    public Integer insert(Sala sala) {
        String sql = "INSERT INTO Sala (nome, capacidade, cinema_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.statement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, sala.getNome());
            stmt.setInt(2, sala.getCapacidade());
            stmt.setInt(3, sala.getCinemaId());
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
        String sql = "DELETE FROM Sala WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Sala sala) {
        String sql = "UPDATE Sala SET nome = ?, capacidade = ?, cinema_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setString(1, sala.getNome());
            stmt.setInt(2, sala.getCapacidade());
            stmt.setInt(3, sala.getCinemaId());
            stmt.setInt(4, sala.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Sala> consulta(String sql) {
        List<Sala> salas = new ArrayList<>();
        try (PreparedStatement stmt = conn.statement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Sala sala = new Sala(rs.getInt("id"), rs.getString("nome"), rs.getInt("capacidade"), rs.getInt("cinema_id"));
                salas.add(sala);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return salas;
    }
}