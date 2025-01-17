package main.java.dao.implem;

import main.java.cinema.Cinema;
import main.java.cinema.CinemaA;
import main.java.cinema.CinemaB;
import main.java.cinema.Sala;
import main.java.cinema.Sessao;
import main.java.cinema.Filme;
import main.java.conexao.DBConn;
import main.java.dao.intefaces.CinemaDAO;
import main.java.conexao.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CinemaDAOImpl implements CinemaDAO {
    private final DBConn conn;

    public CinemaDAOImpl(DBConn conn) {
        this.conn = conn;
    }

    private Cinema createCinemaInstance(int id, String nome, String local) {
        if (nome.startsWith("A")) {
            return CinemaA.getInstance(id, nome, local);
        } else {
            return CinemaB.getInstance(id, nome, local);
        }
    }

    @Override
    public Optional<Cinema> buscarPeloId(Integer id) {
        String sql = "SELECT * FROM Cinema WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cinema cinema = createCinemaInstance(rs.getInt("id"), rs.getString("nome"), rs.getString("local"));
                cinema.setSalas(carregarSalas(cinema.getId()));
                return Optional.of(cinema);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Cinema> listar() {
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT * FROM Cinema";
        try (PreparedStatement stmt = conn.statement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cinema cinema = createCinemaInstance(rs.getInt("id"), rs.getString("nome"), rs.getString("local"));
                cinema.setSalas(carregarSalas(cinema.getId()));
                cinemas.add(cinema);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return cinemas;
    }

    @Override
    public Integer insert(Cinema cinema) {
        String sql = "INSERT INTO Cinema (nome, local) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setString(1, cinema.getNome());
            stmt.setString(2, cinema.getLocal());
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
        String sql = "DELETE FROM Cinema WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Cinema cinema) {
        String sql = "UPDATE Cinema SET nome = ?, local = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setString(1, cinema.getNome());
            stmt.setString(2, cinema.getLocal());
            stmt.setInt(3, cinema.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Cinema> consulta(String sql) {
        List<Cinema> cinemas = new ArrayList<>();
        try (PreparedStatement stmt = conn.statement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cinema cinema = createCinemaInstance(rs.getInt("id"), rs.getString("nome"), rs.getString("local"));
                cinema.setSalas(carregarSalas(cinema.getId()));
                cinemas.add(cinema);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return cinemas;
    }

    private List<Sala> carregarSalas(int cinemaId) {
        List<Sala> salas = new ArrayList<>();
        String sql = "SELECT * FROM Sala WHERE cinema_id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, cinemaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sala sala = new Sala(rs.getInt("id"), rs.getString("nome"), rs.getInt("capacidade"), rs.getInt("cinema_id"));
                sala.setSessoes(carregarSessoes(sala.getId()));
                salas.add(sala);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return salas;
    }

    private List<Sessao> carregarSessoes(int salaId) {
        List<Sessao> sessoes = new ArrayList<>();
        String sql = "SELECT s.id, s.dataHora, sa.id as sala_id, sa.nome as sala_nome, sa.capacidade as sala_capacidade, " +
                "sa.cinema_id, f.id as filme_id, f.nome as filme_nome, f.duracao_s as filme_duracao_s " +
                "FROM Sessao s " +
                "JOIN Sala sa ON s.sala_id = sa.id " +
                "JOIN Filme f ON s.filme_id = f.id " +
                "WHERE s.sala_id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, salaId);
            ResultSet rs = stmt.executeQuery();
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