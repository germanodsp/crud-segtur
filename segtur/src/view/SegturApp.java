package view;

import control.GerenciadorDestino;
import control.GerenciadorPacote;
import control.GerenciadorReserva;
import control.GerenciadorUsuario;
import modelo.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class SegturApp {
    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        DestinoDAO destinoDAO = new DestinoDAO();
        PacoteDAO pacoteDAO = new PacoteDAO();
        ReservaDAO reservaDAO = new ReservaDAO();
        Scanner scanner = new Scanner(System.in);

        preCarregarDados(usuarioDAO, destinoDAO, pacoteDAO);

        System.out.println("===== BEM-VINDO AO SISTEMA SEGTUR =====");
        Usuario usuarioLogado = realizarLogin(usuarioDAO, scanner);
        if (usuarioLogado != null) {
            System.out.println("\nLogin realizado com sucesso! Bem-vindo(a), " + usuarioLogado.nome() + ".");
            exibirMenuPrincipalLoop(usuarioLogado, usuarioDAO, destinoDAO, pacoteDAO, reservaDAO, scanner);
        } else {
            System.out.println("Não foi possível realizar o login. A aplicação será encerrada.");
        }
        scanner.close();
    }

    private static Usuario realizarLogin(UsuarioDAO usuarioDAO, Scanner scanner) {
        int tentativas = 0;
        final int MAX_TENTATIVAS = 3;
        while (tentativas < MAX_TENTATIVAS) {
            System.out.print("Digite seu CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Digite sua senha: ");
            String senha = scanner.nextLine();

            Usuario usuario = usuarioDAO.pesquisarPorCpf(cpf);

            if (usuario != null && usuario.senha().equals(senha)) {
                return usuario; // Sucesso! Retorna o objeto do usuário que fez o login.
            } else {
                tentativas++;
                System.out.println("CPF ou senha inválidos. " + (MAX_TENTATIVAS - tentativas) + " tentativa(s) restante(s).");
            }
        }

        return null;
    }

    private static void exibirMenuPrincipalLoop(Usuario usuarioLogado, UsuarioDAO usuarioDAO, DestinoDAO destinoDAO, PacoteDAO pacoteDAO, ReservaDAO reservaDAO, Scanner scanner) {
        GerenciadorUsuario gerenciadorUsuario = new GerenciadorUsuario(usuarioDAO, scanner);
        GerenciadorDestino gerenciadorDestino = new GerenciadorDestino(destinoDAO, scanner);
        GerenciadorPacote gerenciadorPacote = new GerenciadorPacote(pacoteDAO, destinoDAO, scanner);
        GerenciadorReserva gerenciadorReserva = new GerenciadorReserva(reservaDAO, usuarioDAO, pacoteDAO, scanner, usuarioLogado);

        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao(scanner);

            switch (opcao) {
                case 1 -> gerenciadorUsuario.gerenciar();
                case 2 -> gerenciadorDestino.gerenciar();
                case 3 -> gerenciadorPacote.gerenciar();
                case 4 -> gerenciadorReserva.gerenciar();
                case 0 -> System.out.println("A fazer logout... Obrigado por utilizar o sistema SEGTUR!");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenuPrincipal() {
        clearConsole();
        System.out.println("\n===== SEGTUR TURISMO - MENU PRINCIPAL =====");
        System.out.println("1. Gerenciar Usuários");
        System.out.println("2. Gerenciar Destinos");
        System.out.println("3. Gerenciar Pacotes");
        System.out.println("4. Gerenciar Reservas");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número válido.");
            return -1;
        }
    }

    private static void preCarregarDados(UsuarioDAO usuarioDAO, DestinoDAO destinoDAO, PacoteDAO pacoteDAO) {
        try {
            usuarioDAO.inserir(new Usuario("111", "Ana Clara", "senha12345", "ana@email.com"));
            usuarioDAO.inserir(new Usuario("222", "Bruno Costa", "senha67890", "bruno@email.com"));

            Destino d1 = new Destino(1, "Serra Gaúcha", "Passeios em Gramado e Canela.");
            Destino d2 = new Destino(2, "Rio de Janeiro", "Né mermo");
            destinoDAO.inserir(d1);
            destinoDAO.inserir(d2);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            pacoteDAO.inserir(new Pacote(0, "Inverno na Serra", d1, 2500.00, sdf.parse("10/07/2025"), sdf.parse("15/07/2025"), "Hotel 4 estrelas e passeios inclusos.", 10));
            pacoteDAO.inserir(new Pacote(0, "Carnaval no Rio", d2, 4500.00, sdf.parse("28/02/2026"), sdf.parse("05/03/2026"), "Hospedagem próxima à Sapucaí.", 0));
        } catch(ParseException e) {
            System.out.println("Erro ao carregar dados iniciais (formato de data inválido).");
        }
    }

    public final static void clearConsole()
    {
        for (int i = 0; i < 50; ++i) {
            System.out.println();
        }
    }
}