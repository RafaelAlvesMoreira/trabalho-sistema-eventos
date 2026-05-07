package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaEventos {
    private static final String ARQUIVO_EVENTO = "eventos.txt";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ArrayList<Evento> lista = carregarEventos();

        System.out.println("Bem-vindo ao sistema de eventos\n");

        while (true) {
            mostrarMenu();
            System.out.print("Escolha uma opcao: ");
            String opcao = teclado.nextLine().trim();

            switch (opcao) {
                case "1":
                    cadastrarEvento(teclado, lista);
                    break;
                case "2":
                    listarEventos(lista);
                    break;
                case "3":
                    System.out.println("Saindo do sistema. Ate logo!");
                    teclado.close();
                    return;
                default:
                    System.out.println("Opcao invalida. Digite 1, 2 ou 3.\n");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("--- MENU ---");
        System.out.println("1 - Cadastrar usuario e evento");
        System.out.println("2 - Mostrar eventos salvos");
        System.out.println("3 - Sair\n");
    }

    private static ArrayList<Evento> carregarEventos() {
        File arquivo = new File(ARQUIVO_EVENTO);
        ArrayList<Evento> lista = new ArrayList<>();
        if (!arquivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("\\|");
                if (partes.length == 5) {
                    try {
                        String nome = partes[0];
                        String endereco = partes[1];
                        String categoria = partes[2];
                        LocalDateTime horario = LocalDateTime.parse(partes[3]);
                        String descricao = partes[4];
                        Evento evento = new Evento(nome, endereco, categoria, horario, descricao);
                        lista.add(evento);
                    } catch (Exception e) {
                        // Ignora linhas malformadas
                        System.out.println("Aviso: linha malformada no arquivo, ignorando: " + linha);
                    }
                }
            }
            System.out.println("[OK] " + lista.size() + " evento(s) carregado(s) de " + ARQUIVO_EVENTO + ".\n");
        } catch (IOException e) {
            System.out.println("Aviso: nao foi possivel carregar os eventos do arquivo. Iniciando lista vazia.");
        }

        return lista;
    }

    private static void salvarEventos(ArrayList<Evento> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_EVENTO))) {
            for (Evento evento : lista) {
                bw.write(evento.nome + "|" + evento.endereco + "|" + evento.categoria + "|" + evento.horario + "|" + evento.descricao);
                bw.newLine();
            }
            System.out.println("[OK] Dados salvos com sucesso no arquivo " + ARQUIVO_EVENTO + "!\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage() + "\n");
        }
    }

    private static void cadastrarEvento(Scanner teclado, ArrayList<Evento> lista) {
        System.out.println("--- CADASTRO DE USUARIO ---");
        System.out.print("Nome do estudante: ");
        String nomeU = teclado.nextLine();

        System.out.print("Cidade onde reside: ");
        String cidadeU = teclado.nextLine();

        int idade = lerIdade(teclado);
        Usuario user = new Usuario(nomeU, "estudante@anima.com", cidadeU, idade);

        System.out.println("\n--- CADASTRAR NOVO EVENTO ---");
        System.out.print("Nome do evento: ");
        String nomeE = teclado.nextLine();

        System.out.print("Endereco: ");
        String endE = teclado.nextLine();

        System.out.print("Categoria (Show/Festa/Palestra): ");
        String catE = teclado.nextLine();

        System.out.print("Descricao: ");
        String descE = teclado.nextLine();

        Evento novo = new Evento(nomeE, endE, catE, LocalDateTime.now().plusDays(2), descE);
        lista.add(novo);
        salvarEventos(lista);

        System.out.println("--- CONFIRMACAO DE PRESENCA ---");
        System.out.println("Usuario: " + user.nome + " | Evento: " + novo.nome);
        System.out.print("Deseja cancelar sua participacao? (S/N): ");
        String resposta = teclado.nextLine();
        if (resposta.equalsIgnoreCase("S")) {
            lista.remove(novo);
            salvarEventos(lista);
            System.out.println("Participacao cancelada com sucesso!\n");
        } else {
            System.out.println("Presenca confirmada!\n");
        }

        System.out.println("--- RESUMO DO CADASTRO ---");
        System.out.println("Estudante: " + user.nome + " de " + user.cidade + " | Idade: " + user.idade);
        System.out.println("Evento: " + novo.nome + " em " + novo.endereco + "\n");
    }

    private static int lerIdade(Scanner teclado) {
        while (true) {
            System.out.print("Idade: ");
            try {
                int idade = Integer.parseInt(teclado.nextLine().trim());
                if (idade >= 18) {
                    return idade;
                }
                System.out.println("Somente maiores de 18 anos podem usar o sistema. Tente novamente.\n");
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um numero inteiro para a idade.\n");
            }
        }
    }

    private static void listarEventos(ArrayList<Evento> lista) {
        System.out.println("--- EVENTOS SALVOS ---");
        if (lista.isEmpty()) {
            System.out.println("Ainda nao ha eventos cadastrados.\n");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            Evento evento = lista.get(i);
            System.out.printf("%d) %s - %s - %s\n", i + 1, evento.nome, evento.categoria, evento.getStatus());
            System.out.println("   Endereco: " + evento.endereco);
            System.out.println("   Descricao: " + evento.descricao);
            System.out.println("   Horario: " + evento.horario + "\n");
        }
    }
}
