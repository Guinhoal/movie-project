package main.java.cinema;

import java.util.ArrayList;
import java.util.List;

public class CinemaB extends Cinema {
    private static CinemaB instance;
    private static List<Cinema> cinemas = new ArrayList<>();

    private CinemaB(int id, String nome, String local) {
        super(id, nome, local);
        cinemas.add(this);
    }

    public static CinemaB getInstance(int id, String nome, String local) {
        if (instance == null) {
            instance = new CinemaB(id, nome, local);
        }
        return instance;
    }

    @Override
    public void criarSala(int id, String nome, int capacidade) {
        Sala sala = new Sala(id, nome, capacidade, this.id);
        salas.add(sala);
    }

    @Override
    public List<Sala> listarSalas() {
        return salas;
    }

    @Override
    public List<Cinema> listarCinemas() {
        return cinemas;
    }

    @Override
    public String toString() {
        return String.format("Cinema ID: %d\nNome: %s\nLocal: %s", id, nome, local);
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
}