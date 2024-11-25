package main.java.cinema

import java.util.ArrayList;
import java.util.List;

public class CinemaConcretoA extends Cinema {
    private static CinemaConcretoA instance;
    private static List<Cinema> cinemas = new ArrayList<>();

    private CinemaConcretoA(int id, String nome, String local) {
        super(id, nome, local);
        cinemas.add(this);
    }

    public static CinemaConcretoA getInstance(int id, String nome, String local) {
        if (instance == null) {
            instance = new CinemaConcretoA(id, nome, local);
        }
        return instance;
    }

    @Override
    public void criarSala(String nome, int capacidade) {
        Sala sala = new Sala(nome, capacidade);
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
}