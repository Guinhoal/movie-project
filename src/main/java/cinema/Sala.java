package main.java.cinema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int id;
    private String nome;
    private int capacidade;
    private int cinemaId;
    private List<Sessao> sessoes;

    public Sala(int id, String nome, int capacidade, int cinemaId) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.sessoes = new ArrayList<>();
        this.cinemaId = cinemaId;
    }

    public void criarSessao(int id, Filme filme, LocalDateTime dataHora) {
        Sessao sessao = new Sessao(id, this, filme, dataHora);
        sessoes.add(sessao);
    }

    public List<Sessao> listarSessoes() {
        return sessoes;
    }

    public void setSessoes(List<Sessao> sessoes) {
        this.sessoes = sessoes;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }
    public int getId() { return id; }

    @Override
    public String toString() {
        return String.format("Sala ID: %d\nNome: %s\nCapacidade: %d", id, nome, capacidade);
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }
}
