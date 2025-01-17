package main.java.cinema;
import java.time.LocalDateTime;
import java.time.Duration;

public class Sessao {
    private int id;
    private Sala sala;
    private Filme filme;
    private LocalDateTime dataHora;

    public Sessao(int id, Sala sala, Filme filme, LocalDateTime dataHora) {
        this.id = id;
        this.sala = sala;
        this.filme = filme;
        this.dataHora = dataHora;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    public Filme getFilme() { return filme; }
    public void setFilme(Filme filme) { this.filme = filme; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getDuracaoTotal() {
        Duration duracao = Duration.ofSeconds(filme.getDuracao_s());
        long horas = duracao.toHours();
        long minutos = duracao.toMinutes() % 60;
        return String.format("%d horas e %d minutos", horas, minutos);
    }

    @Override
    public String toString() {
        return String.format("Sala: %s\nFilme: %s\nData e Hora: %s\nDuração Total: %s"
                , sala.getNome(), filme.getNome(), dataHora, getDuracaoTotal() + "\n" + "---------------------------------------------");
    }
}