package main.java;

import main.java.cinema.Cinema;
import main.java.cinema.Filme;
import main.java.cinema.Sala;
import main.java.cinema.Sessao;
import main.java.conexao.ConnectionFactory;
import main.java.conexao.DBConn;
import main.java.dao.implem.CinemaDAOImpl;
import main.java.dao.implem.FilmeDAOImpl;
import main.java.dao.implem.SalaDAOImpl;
import main.java.dao.implem.SessaoDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Teste {
    public static void main(String[] args) {
        DBConn conn = ConnectionFactory.getConnection();

        CinemaDAOImpl cinemaDAO = new CinemaDAOImpl(conn);
        FilmeDAOImpl filmeDAO = new FilmeDAOImpl(conn);
        SalaDAOImpl salaDAO = new SalaDAOImpl(conn);
        SessaoDAOImpl sessaoDAO = new SessaoDAOImpl(conn);

        List<Cinema> cinemas = cinemaDAO.listar();
        System.out.println("Cinemas carregados: " + cinemas);

        List<Filme> filmes = filmeDAO.listar();
        System.out.println("Filmes carregados: " + filmes);

        List<Sala> salas = salaDAO.listar();
        System.out.println("Salas carregadas: " + salas);

        List<Sessao> sessoes = sessaoDAO.listar();
        System.out.println("Sessões carregadas: " + sessoes);


    }
}