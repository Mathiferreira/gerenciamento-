import contato.Contato;
import exception.ContatoExistenteException;
import exception.ContatoNaoEncontradoException;
import java.util.List;
import java.util.Scanner;
import service.AgendaManager;
import service.GerenciadorContatos;

public class App {

    private static final GerenciadorContatos gerenciador = new AgendaManager();
    private static Scanner scanner;

    public static void main(String[] args) {
        System.out.println("Bem-vindo à Agenda Eletrônica!");
        int opcao = -1;
        try (Scanner s = new Scanner(System.in)) {
            scanner = s;
            while (opcao != 9) {
                exibirMenu();
                try {
                    opcao = Integer.parseInt(scanner.nextLine());
                    processarOpcao(opcao);
                } catch (NumberFormatException e) {
                    System.out.println("Opção inválida. Por favor, digite um número.");
                }
            }
        }
        System.out.println("Saindo da aplicação. Até mais!");
    }
    
    private static void exibirMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Adicionar Contato");
        System.out.println("2. Buscar Contato");
        System.out.println("3. Remover Contato");
        System.out.println("4. Listar Todos os Contatos");
        System.out.println("5. Listar Contatos Ordenados por Nome");
        System.out.println("6. Buscar por Domínio de E-mail");
        System.out.println("7. Salvar Contatos em CSV");
        System.out.println("8. Carregar Contatos de CSV");
        System.out.println("9. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> adicionarContato();
            case 2 -> buscarContato();
            case 3 -> removerContato();
            case 4 -> listarTodosContatos();
            case 5 -> listarContatosOrdenados();
            case 6 -> buscarPorDominioEmail();
            case 7 -> salvarContatosCSV();
            case 8 -> carregarContatosCSV();
            case 9 -> { // Apenas sai do loop no main
            }
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void adicionarContato() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        try {
            gerenciador.adicionarContato(new Contato(nome, telefone, email));
            System.out.println("Contato adicionado com sucesso!");
        } catch (ContatoExistenteException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void buscarContato() {
        System.out.print("Digite o nome do contato para buscar: ");
        String nome = scanner.nextLine();
        try {
            Contato contato = gerenciador.buscarContato(nome);
            System.out.println("Contato encontrado: " + contato);
        } catch (ContatoNaoEncontradoException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void removerContato() {
        System.out.print("Digite o nome do contato para remover: ");
        String nome = scanner.nextLine();
        try {
            gerenciador.removerContato(nome);
            System.out.println("Contato removido com sucesso!");
        } catch (ContatoNaoEncontradoException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void listarTodosContatos() {
        List<Contato> contatos = gerenciador.listarTodosContatos();
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato na agenda.");
        } else {
            System.out.println("\n--- Lista de Contatos ---");
            contatos.forEach(System.out::println);
        }
    }

    private static void listarContatosOrdenados() {
        List<Contato> contatos = gerenciador.listarContatosOrdenados();
         if (contatos.isEmpty()) {
            System.out.println("Nenhum contato na agenda.");
        } else {
            System.out.println("\n--- Lista de Contatos Ordenados por Nome ---");
            contatos.forEach(System.out::println);
        }
    }

    private static void buscarPorDominioEmail() {
        System.out.print("Digite o domínio do e-mail (ex: gmail.com): ");
        String dominio = scanner.nextLine();
        List<Contato> contatos = gerenciador.buscarPorDominioEmail(dominio);
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato encontrado com o domínio @" + dominio);
        } else {
            System.out.println("\n--- Contatos com domínio @" + dominio + " ---");
            contatos.forEach(System.out::println);
        }
    }

    private static void salvarContatosCSV() {
        System.out.print("Digite o nome do arquivo para salvar (ex: contatos.csv): ");
        String nomeArquivo = scanner.nextLine();
        gerenciador.salvarContatosCSV(nomeArquivo);
        System.out.println("Contatos salvos em '" + nomeArquivo + "' com sucesso!");
    }

    private static void carregarContatosCSV() {
        System.out.print("Digite o nome do arquivo para carregar (ex: contatos.csv): ");
        String nomeArquivo = scanner.nextLine();
        gerenciador.carregarContatosCSV(nomeArquivo);
        System.out.println("Contatos carregados de '" + nomeArquivo + "'.");
    }
}