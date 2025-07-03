//package view;
//
//import modelo.Pacote;
//import modelo.Usuario;
//import modelo.PacoteDAO; // Supondo que você criou PacoteDAO como sugeri
//import modelo.UsuarioDAO; // Supondo que você criou UsuarioDAO como sugeri
//
//import java.util.Date;
//import java.util.InputMismatchException;
//import java.util.List;
//import java.util.Scanner;
//
//public class Segtur {
//
//    // Scanner é estático para ser usado em toda a classe
//    private static Scanner scanner = new Scanner(System.in);
//
//    // Instâncias dos DAOs para manipular os dados
//    private static PacoteDAO pacoteDAO = new PacoteDAO();
//    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
//
//    public static void main(String[] args) {
//        // Carrega alguns dados de exemplo para o sistema não começar vazio
//        carregarDadosIniciais();
//
//        int opcao = -1;
//        while (opcao != 0) {
//            exibirMenuPrincipal();
//            try {
//                opcao = scanner.nextInt();
//                scanner.nextLine(); // Limpa o buffer do scanner
//
//                switch (opcao) {
//                    case 1:
//                        gerenciarPacotes();
//                        break;
//                    case 2:
//                        gerenciarUsuarios();
//                        break;
//                    case 3:
//                        // Chamar o método para gerenciar reservas
//                        System.out.println("\n(Funcionalidade de Reservas a ser implementada)");
//                        break;
//                    case 4:
//                        // Chamar o método para gerenciar destinos
//                        System.out.println("\n(Funcionalidade de Destinos a ser implementada)");
//                        break;
//                    case 0:
//                        System.out.println("\nObrigado por usar o sistema SEGTUR. Até logo!");
//                        break;
//                    default:
//                        System.out.println("\nOpção inválida! Por favor, tente novamente.");
//                        break;
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("\nErro: Por favor, digite apenas números para selecionar uma opção.");
//                scanner.nextLine(); // Limpa o buffer para evitar loop infinito
//                opcao = -1; // Reseta a opção para continuar no loop
//            }
//            pressioneEnterParaContinuar();
//        }
//        scanner.close();
//    }
//
//    private static void exibirMenuPrincipal() {
//        System.out.println("\n--- BEM-VINDO AO SISTEMA SEGTUR TURISMO ---");
//        System.out.println("Selecione uma opção:");
//        System.out.println("1 - Gerenciar Pacotes de Viagem");
//        System.out.println("2 - Gerenciar Usuários");
//        System.out.println("3 - Gerenciar Reservas");
//        System.out.println("4 - Gerenciar Destinos");
//        System.out.println("0 - Sair do Sistema");
//        System.out.print("Sua opção: ");
//    }
//
//    private static void gerenciarPacotes() {
//        int opcao = -1;
//        while(opcao != 9) {
//            System.out.println("\n--- GERENCIAR PACOTES ---");
//            System.out.println("1 - Listar todos os pacotes");
//            System.out.println("2 - Cadastrar novo pacote");
//            System.out.println("9 - Voltar ao menu principal");
//            System.out.print("Sua opção: ");
//
//            try {
//                opcao = scanner.nextInt();
//                scanner.nextLine(); // Limpa o buffer
//
//                switch(opcao) {
//                    case 1:
//                        listarTodosPacotes();
//                        break;
//                    case 2:
//                        cadastrarNovoPacote();
//                        break;
//                    case 9:
//                        System.out.println("Voltando ao menu principal...");
//                        break;
//                    default:
//                        System.out.println("Opção inválida!");
//                        break;
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("\nErro: Digite apenas números.");
//                scanner.nextLine(); // Limpa o buffer
//                opcao = -1; // Reseta a opção
//            }
//            if(opcao != 9) {
//                pressioneEnterParaContinuar();
//            }
//        }
//    }
//
//    private static void listarTodosPacotes() {
//        System.out.println("\n--- LISTA DE PACOTES DISPONÍVEIS ---");
//        List<Pacote> pacotes = pacoteDAO.buscarTodos();
//
//        if (pacotes.isEmpty()) {
//            System.out.println("Nenhum pacote cadastrado no momento.");
//        } else {
//            for (Pacote pacote : pacotes) {
//                // O método toString() da classe Pacote será usado aqui
//                System.out.println(pacote);
//            }
//        }
//    }
//
//    private static void cadastrarNovoPacote() {
//        System.out.println("\n--- CADASTRO DE NOVO PACOTE ---");
//        try {
//            System.out.print("Nome do Pacote: ");
//            String nome = scanner.nextLine();
//
//            System.out.print("Destino: ");
//            String destino = scanner.nextLine();
//
//            System.out.print("Preço (ex: 2500.50): ");
//            double preco = scanner.nextDouble();
//
//            System.out.print("Vagas disponíveis: ");
//            int vagas = scanner.nextInt();
//            scanner.nextLine(); // Limpa o buffer
//
//            System.out.print("Itinerário (descrição completa): ");
//            String itinerario = scanner.nextLine();
//
//            // Criando o pacote (simplificado sem data por enquanto)
//            Pacote novoPacote = new Pacote(0, nome, destino, preco, null, null, itinerario, vagas);
//            pacoteDAO.inserir(novoPacote);
//
//            System.out.println("\nPacote cadastrado com sucesso!");
//
//        } catch (InputMismatchException e) {
//            System.out.println("\nErro de digitação. O preço e as vagas devem ser números. Tente novamente.");
//            scanner.nextLine(); // Limpa o buffer
//        }
//    }
//
//    // Este é um exemplo de como seria o método para gerenciar usuários
//    private static void gerenciarUsuarios() {
//        System.out.println("\n--- GERENCIAR USUÁRIOS ---");
//        System.out.println("1 - Cadastrar novo usuário");
//        System.out.println("2 - Listar todos os usuários");
//        System.out.println("9 - Voltar");
//        System.out.print("Sua opção: ");
//        // A lógica de menu (switch-case) seria implementada aqui, similar ao gerenciarPacotes()
//    }
//
//    private static void pressioneEnterParaContinuar() {
//        System.out.println("\nPressione ENTER para continuar...");
//        scanner.nextLine();
//    }
//
//    private static void carregarDadosIniciais() {
//        // Carga de Pacotes
//        Pacote p1 = new Pacote(1, "Serras Gaúchas", 1800.0, new Date(), new Date());
//        //Pacote p1 = new Pacote(1, "Serras Gaúchas", "Gramado/Canela", 1800.0, null, null, "Visita a vinícolas e parques.", 15);
//        Pacote p2 = new Pacote(2, "Praias do Nordeste", "Porto de Galinhas", 3200.0, null, null, "Passeio de jangada e mergulho.", 10);
//        pacoteDAO.inserir(p1);
//        pacoteDAO.inserir(p2);
//
//        // Carga de Usuário
//        Usuario u1 = new Usuario("12345678900", "João da Silva", "123456", "joao@email.com", "51999998888");
//        usuarioDAO.inserir(u1);
//    }
//}