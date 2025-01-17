package main.java.dao.implem;

import main.java.servico.VendaIngresso;
import main.java.cinema.Sessao;
import main.java.conexao.DBConn;
import main.java.dao.intefaces.VendaIngressoDAO;
import main.java.conexao.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VendaIngressoDAOImpl implements VendaIngressoDAO {
    private final DBConn conn;

    public VendaIngressoDAOImpl(DBConn conn) {
        this.conn = conn;
    }

    @Override
    public Optional<VendaIngresso> buscarPeloId(Integer id) {
        String sql = "SELECT * FROM VendaIngresso WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Sessao sessao = new Sessao(rs.getInt("sessao_id"), null, null, null); // Adjust as needed
                VendaIngresso venda = new VendaIngresso(rs.getInt("id"), sessao, rs.getInt("quantidade"));
                return Optional.of(venda);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<VendaIngresso> listar() {
        List<VendaIngresso> vendas = new ArrayList<>();
        String sql = "SELECT * FROM VendaIngresso";
        try (PreparedStatement stmt = conn.statement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Sessao sessao = new Sessao(rs.getInt("sessao_id"), null, null, null); // Adjust as needed
                VendaIngresso venda = new VendaIngresso(rs.getInt("id"), sessao, rs.getInt("quantidade"));
                vendas.add(venda);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return vendas;
    }

    @Override
    public Integer insert(VendaIngresso venda) {
        String sql = "INSERT INTO VendaIngresso (sessao_id, quantidade) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, venda.getSessao().getId());
            stmt.setInt(2, venda.getQuantidade());
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
        String sql = "DELETE FROM VendaIngresso WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(VendaIngresso venda) {
        String sql = "UPDATE VendaIngresso SET sessao_id = ?, quantidade = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.statement(sql)) {
            stmt.setInt(1, venda.getSessao().getId());
            stmt.setInt(2, venda.getQuantidade());
            stmt.setInt(3, venda.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<VendaIngresso> consulta(String sql) {
        List<VendaIngresso> vendas = new ArrayList<>();
        try (PreparedStatement stmt = conn.statement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Sessao sessao = new Sessao(rs.getInt("sessao_id"), null, null, null); // Adjust as needed
                VendaIngresso venda = new VendaIngresso(rs.getInt("id"), sessao, rs.getInt("quantidade"));
                vendas.add(venda);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return vendas;
    }
}