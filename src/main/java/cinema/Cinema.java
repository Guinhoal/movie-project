package main.java.cinema;

import java.util.ArrayList;
import java.util.List;

public abstract class Cinema {
    protected int id;
    protected String nome;
    protected String local;
    protected List<Sala> salas;

    public Cinema(int id, String nome, String local) {
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.salas = new ArrayList<>();
    }

    public abstract void criarSala(int id, String nome, int capacidade);
    public List<Sala> listarSalas(){
        return salas;
    };
    public abstract List<Cinema> listarCinemas();
    public List<Sessao> listarSessoes() {
        List<Sessao> todasSessoes = new ArrayList<>();
        for (Sala sala : salas) {
            todasSessoes.addAll(sala.listarSessoes());
        }
        return todasSessoes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }
}