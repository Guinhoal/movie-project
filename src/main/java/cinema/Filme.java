package main.java.cinema;

public class Filme {
    private int id;
    private String nome;
    private long duracao_s;

    public Filme(int id, String nome, long duracao_s) {
        this.id = id;
        this.nome = nome;
        this.duracao_s = duracao_s;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public long getDuracao_s() { return duracao_s; }
    public void setDuracao_s(long duracao_s) { this.duracao_s = duracao_s; }

    @Override
    public String toString() {
        return String.format("Nome: %s \nDuração: %d segundos\n----------------------------------------------", nome, duracao_s);
    }
}