package main.java;

import main.java.cinema.*;
import main.java.conexao.ConnectionFactory;
import main.java.conexao.DBConn;
import main.java.dao.implem.*;
import main.java.servico.VendaIngresso;
import main.java.exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class CinemaMain {
    private static DBConn conn;
    private static CinemaDAOImpl cinemaDAO;
    private static FilmeDAOImpl filmeDAO;
    private static SalaDAOImpl salaDAO;
    private static SessaoDAOImpl sessaoDAO;

    private static List<Cinema> cinemas;
    private static List<Filme> filmes;
    private static List<Sala> salas;
    private static List<Sessao> sessoes;

    private static Scanner scan = new Scanner(System.in);
    private static int optCinema = -1;
    private static int optEscolha = -1;

    static {
        conn = ConnectionFactory.getConnection();
        cinemaDAO = new CinemaDAOImpl(conn);
        filmeDAO = new FilmeDAOImpl(conn);
        salaDAO = new SalaDAOImpl(conn);
        sessaoDAO = new SessaoDAOImpl(conn);

        cinemas = cinemaDAO.listar();
        filmes = filmeDAO.listar();
        salas = salaDAO.listar();
        sessoes = sessaoDAO.listar();
    }

    public static void main(String[] args) {
        Cinema cinemaEscolhido = escolhaCinema();
        while (true) {
            mostrarMenu();
            optEscolha = scan.nextInt();
            executarAcao(optEscolha, cinemaEscolhido);
        }
    }

    public static Cinema escolhaCinema() {
        while (optCinema < 1 || optCinema > cinemas.size()) {
            System.out.println("----* Seja Bem - Vindo ao Sistema Gelado Filmes *----");
            System.out.println("Selecione um cinema para podermos continuar: ");

            for (int i = 0; i < cinemas.size(); i++) {
                System.out.println((i + 1) + " - " + cinemas.get(i).getNome());
            }

            optCinema = scan.nextInt();
            if (optCinema < 1 || optCinema > cinemas.size()) {
                System.out.println("Opção inválida. Por favor, selecione um cinema válido.");
            }
        }

        Cinema cinemaSelecionado = cinemas.get(optCinema - 1);
        System.out.println("Cinema selecionado: " + cinemaSelecionado.getNome());
        return cinemaSelecionado;
    }

    public static void mostrarMenu() {
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Listar sessões disponíveis");
        System.out.println("2 - Listar filmes em cartaz");
        System.out.println("3 - Comprar ingressos");
        System.out.println("4 - Verificar status de uma sala");
        System.out.println("5 - Criar novas coisas (Salas, Sessões, Filmes)");
        System.out.println("6 - Verificar quando um filme acabará");
        System.out.println("7 - Selecionar outro cinema");
        System.out.println("0 - Sair");
    }

    public static void executarAcao(int optEscolha, Cinema cinemaEscolhido) {
        switch (optEscolha) {
            case 1:
                listarSessoes(cinemaEscolhido);
                break;
            case 2:
                listarFilmes();
                break;
            case 3:
                comprarIngressos(cinemaEscolhido);
                break;
            case 4:
                verificarStatusSala(cinemaEscolhido);
                break;
            case 5:
                criarNovasCoisas(cinemaEscolhido);
                break;
            case 6:
                verificarFimFilme(cinemaEscolhido);
                break;
            case 7:
                optCinema = -1;
                cinemaEscolhido = escolhaCinema();
                break;
            case 0:
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
        }
    }

    public static void listarSessoes(Cinema cinemaEscolhido) {
        System.out.println("Sessões disponíveis:");
        for (Sala sala : cinemaEscolhido.listarSalas()) {
            for (Sessao sessao : sala.listarSessoes()) {
                System.out.println(sessao);
            }
        }
    }

    public static void listarFilmes() {
        System.out.println("Filmes em cartaz:");
        for (Filme filme : filmes) {
            System.out.println(filme);
        }
    }

    public static void comprarIngressos(Cinema cinemaEscolhido) {
        System.out.println("Sessões disponíveis:");
        List<Sessao> sessoesCinema = new ArrayList<>();
        for (Sala sala : cinemaEscolhido.listarSalas()) {
            sessoesCinema.addAll(sala.listarSessoes());
        }

        for (int i = 0; i < sessoesCinema.size(); i++) {
            System.out.println((i + 1) + " - " + sessoesCinema.get(i));
        }

        System.out.println("Selecione uma sessão:");
        int optSessao = scan.nextInt();
        if (optSessao < 1 || optSessao > sessoesCinema.size()) {
            System.out.println("Opção inválida. Por favor, selecione uma sessão válida.");
            return;
        }

        Sessao sessaoSelecionada = sessoesCinema.get(optSessao - 1);
        Sala salaSelecionada = sessaoSelecionada.getSala();

        System.out.println("Digite o número de ingressos que deseja comprar:");
        int numIngressos = scan.nextInt();
        if (numIngressos < 1) {
            System.out.println("Número de ingressos inválido.");
            return;
        }

        int lugaresDisponiveis = salaSelecionada.getCapacidade() - calcularIngressosVendidos(sessaoSelecionada);
        if (numIngressos > lugaresDisponiveis) {
            System.out.println("Não há lugares suficientes disponíveis. Lugares disponíveis: " + lugaresDisponiveis);
            return;
        }

        double precoIngresso = 20.0;
        double valorTotal = numIngressos * precoIngresso;

        System.out.println("Sessão selecionada: " + sessaoSelecionada);
        System.out.println("Número de ingressos: " + numIngressos);
        System.out.println("Preço por ingresso: R$ " + precoIngresso);
        System.out.println("Valor total: R$ " + valorTotal);

        System.out.println("Confirmar compra? (s/n)");
        String confirmacao = scan.next();
        if (confirmacao.equalsIgnoreCase("s")) {
            new VendaIngresso(VendaIngresso.listarVendas().size() + 1, sessaoSelecionada, numIngressos);
            System.out.println("Compra realizada com sucesso!");
        } else {
            System.out.println("Compra cancelada.");
        }
    }

    private static int calcularIngressosVendidos(Sessao sessao) {
        int ingressosVendidos = 0;
        for (VendaIngresso venda : VendaIngresso.listarVendas()) {
            if (venda.getSessao().equals(sessao)) {
                ingressosVendidos += venda.getQuantidade();
            }
        }
        return ingressosVendidos;
    }

    public static void verificarStatusSala(Cinema cinemaEscolhido) {
        System.out.println("Salas disponíveis no cinema " + cinemaEscolhido.getNome() + ":");
        List<Sala> salasCinema = cinemaEscolhido.listarSalas();
        for (int i = 0; i < salasCinema.size(); i++) {
            System.out.println((i + 1) + " - " + salasCinema.get(i).getNome());
        }

        System.out.println("Selecione uma sala:");
        int optSala = scan.nextInt();
        if (optSala < 1 || optSala > salasCinema.size()) {
            System.out.println("Opção inválida. Por favor, selecione uma sala válida.");
            return;
        }

        Sala salaSelecionada = salasCinema.get(optSala - 1);
        System.out.println("Sala selecionada: " + salaSelecionada.getNome());
        System.out.println("Capacidade: " + salaSelecionada.getCapacidade());

        System.out.println("Digite a data (formato: yyyy-MM-dd) para verificar as sessões:");
        String dataInput = scan.next();
        LocalDate data;
        try {
            data = LocalDate.parse(dataInput, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Por favor, use o formato yyyy-MM-dd.");
            return;
        }

        System.out.println("Sessões programadas para " + data + ":");
        for (Sessao sessao : salaSelecionada.listarSessoes()) {
            if (sessao.getDataHora().toLocalDate().equals(data)) {
                System.out.println(sessao);
            }
        }
    }

    public static void criarNovasCoisas(Cinema cinemaEscolhido) {
        System.out.println("Escolha o que deseja criar:");
        System.out.println("1 - Nova Sala");
        System.out.println("2 - Novo Filme");
        System.out.println("3 - Nova Sessão");

        int optCriar = scan.nextInt();
        switch (optCriar) {
            case 1:
                criarSala(cinemaEscolhido);
                break;
            case 2:
                criarFilme();
                break;
            case 3:
                criarSessao(cinemaEscolhido);
                break;
            default:
                System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
        }
    }

    public static void criarSala(Cinema cinemaEscolhido) {
        System.out.println("Digite o nome da nova sala:");
        scan.nextLine();
        String nomeSala = scan.nextLine();
        System.out.println("Digite a capacidade da nova sala:");
        int capacidade = scan.nextInt();

        try {
            for (Sala sala : cinemaEscolhido.listarSalas()) {
                if (sala.getNome().equalsIgnoreCase(nomeSala)) {
                    throw new NomeDuplicadoException("Já existe uma sala com esse nome.");
                }
            }
            Sala novaSala = new Sala(cinemaEscolhido.listarSalas().size() + 1, nomeSala, capacidade, cinemaEscolhido.getId());
            cinemaEscolhido.listarSalas().add(novaSala);
            salaDAO.insert(novaSala);
            System.out.println("Sala criada com sucesso!");
        } catch (NomeDuplicadoException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void criarFilme() {
        System.out.println("Digite o nome do novo filme:");
        scan.nextLine();
        String nomeFilme = scan.nextLine();
        System.out.println("Digite a duração do novo filme (em segundos):");
        long duracao = scan.nextLong();

        Filme novoFilme = new Filme(0, nomeFilme, duracao);
        int filmeId = filmeDAO.insert(novoFilme);
        novoFilme.setId(filmeId);
        filmes.add(novoFilme);
        System.out.println("Filme criado com sucesso!");
    }

    public static void criarSessao(Cinema cinemaEscolhido) {
        System.out.println("Salas disponíveis:");
        List<Sala> salasCinema = cinemaEscolhido.listarSalas();
        for (int i = 0; i < salasCinema.size(); i++) {
            System.out.println((i + 1) + " - " + salasCinema.get(i).getNome());
        }

        System.out.println("Selecione uma sala:");
        int optSala = scan.nextInt();
        if (optSala < 1 || optSala > salasCinema.size()) {
            System.out.println("Opção inválida. Por favor, selecione uma sala válida.");
            return;
        }

        Sala salaSelecionada = salasCinema.get(optSala - 1);

        System.out.println("Filmes disponíveis:");
        for (int i = 0; i < filmes.size(); i++) {
            System.out.println((i + 1) + " - " + filmes.get(i).getNome());
        }

        System.out.println("Selecione um filme:");
        int optFilme = scan.nextInt();
        if (optFilme < 1 || optFilme > filmes.size()) {
            System.out.println("Opção inválida. Por favor, selecione um filme válido.");
            return;
        }

        Filme filmeSelecionado = filmes.get(optFilme - 1);

        System.out.println("Digite a data e hora da sessão (formato: yyyy-MM-ddTHH:mm):");
        scan.nextLine();
        String dataHoraInput = scan.nextLine();
        LocalDateTime dataHora;
        try {
            dataHora = LocalDateTime.parse(dataHoraInput, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            System.out.println("Data e hora inválidas. Por favor, use o formato yyyy-MM-ddTHH:mm.");
            return;
        }

        Sessao novaSessao = new Sessao(sessoes.size() + 1, salaSelecionada, filmeSelecionado, dataHora);
        sessoes.add(novaSessao);
        sessaoDAO.insert(novaSessao);
        salaSelecionada.criarSessao(novaSessao.getId(), filmeSelecionado, dataHora);
        System.out.println("Sessão criada com sucesso!");
    }

    public static void verificarFimFilme(Cinema cinemaEscolhido) {
        System.out.println("Selecione uma sessão para ver o horário de término:");
        List<Sessao> sessoesCinema = new ArrayList<>();
        for (Sala sala : cinemaEscolhido.listarSalas()) {
            sessoesCinema.addAll(sala.listarSessoes());
        }

        for (int i = 0; i < sessoesCinema.size(); i++) {
            System.out.println((i + 1) + " - " + sessoesCinema.get(i));
        }

        int optSessao = scan.nextInt();
        if (optSessao < 1 || optSessao > sessoesCinema.size()) {
            System.out.println("Opção inválida. Por favor, selecione uma sessão válida.");
            return;
        }

        Sessao sessaoSelecionada = sessoesCinema.get(optSessao - 1);
        LocalDateTime horarioInicio = sessaoSelecionada.getDataHora();
        LocalDateTime horarioFim = horarioInicio.plusSeconds(sessaoSelecionada.getFilme().getDuracao_s());

        System.out.println("Horário de início: " + horarioInicio);
        System.out.println("Horário de término: " + horarioFim);
    }
}