package main.java.servico;

import main.java.cinema.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaIngresso {
    private static List<VendaIngresso> vendas = new ArrayList<>();
    private int id;
    private Sessao sessao;
    private int quantidade;

    public VendaIngresso(int id, Sessao sessao, int quantidade) {
        this.id = id;
        this.sessao = sessao;
        this.quantidade = quantidade;
        vendas.add(this);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Sessao getSessao() { return sessao; }
    public void setSessao(Sessao sessao) { this.sessao = sessao; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public static List<VendaIngresso> listarVendas() {
        return vendas;
    }

    @Override
    public String toString() {
        return String.format("Venda ID: %d\nSessão: %s\nQuantidade: %d", id, sessao, quantidade);
    }

}