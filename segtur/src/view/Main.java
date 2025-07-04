package view;

import modelo.Destino;
import modelo.DestinoDAO;
import modelo.Pacote;
import modelo.PacoteDAO;
import modelo.Reserva;
import modelo.ReservaDAO;
import modelo.StatusReserva;
import modelo.Usuario;
import modelo.UsuarioDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal que inicia a aplicação e controla o fluxo de interação com o usuário.
 * Responsável por apresentar menus, ler dados e chamar os métodos apropriados dos DAOs.
 */
public class Main {

    // DAOs para manipular os dados do sistema.
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final DestinoDAO destinoDAO = new DestinoDAO();
    private static final PacoteDAO pacoteDAO = new PacoteDAO();
    private static final ReservaDAO reservaDAO = new ReservaDAO();

    // Scanner para ler a entrada do usuário de forma global.
    private static final Scanner scanner = new Scanner(System.in);

    // Armazena o usuário que fez login na sessão atual.
    private static Usuario usuarioLogado = null;

    /**
     * Ponto de entrada do programa (entry point).
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        carregarDadosIniciais(); // Cria dados de teste para facilitar a demonstração.
        executar(); // Inicia o loop principal do menu.
        scanner.close(); // Fecha o recurso do scanner ao finalizar a aplicação.
    }

    /**
     * Loop principal que exibe o menu apropriado (logado ou deslogado)
     * e processa a escolha do usuário até que ele decida sair.
     */
    private static void executar() {
        boolean rodando = true;
        while (rodando) {
            if (usuarioLogado == null) {
                exibirMenuDeslogado();
                int opcao = lerOpcao();

                switch (opcao) {
                    case 1 -> fazerLogin();
                    case 2 -> cadastrarNovoUsuario();
                    case 3 -> rodando = false; // Encerra o loop e o programa.
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } else {
                exibirMenuLogado();
                int opcao = lerOpcao();

                switch (opcao) {
                    case 1 -> listarPacotes();
                    case 2 -> realizarReserva();
                    case 3 -> consultarMinhasReservas();
                    case 4 -> fazerLogout();
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }
        System.out.println("\nObrigado por usar a SEGTUR. Volte sempre!");
    }

    /**
     * Exibe o menu para usuários que ainda não fizeram login.
     */
    private static void exibirMenuDeslogado() {
        System.out.println("\n========================================");
        System.out.println("==        SEGTUR - Bem-vindo(a)!      ==");
        System.out.println("========================================");
        System.out.println("1. Fazer Login");
        System.out.println("2. Cadastrar Novo Usuário");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Exibe o menu principal para usuários que já estão logados.
     */
    private static void exibirMenuLogado() {
        System.out.println("\n========================================");
        System.out.println("==        SEGTUR - Menu Principal     ==");
        System.out.println("========================================");
        System.out.println("Olá, " + usuarioLogado.nome() + "!");
        System.out.println("1. Listar todos os pacotes de viagem");
        System.out.println("2. Fazer uma reserva");
        System.out.println("3. Consultar minhas reservas");
        System.out.println("4. Fazer Logout");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Lê um número inteiro do console de forma segura, tratando exceções.
     * @return O número lido ou -1 em caso de erro.
     */
    private static int lerOpcao() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha pendente após a leitura do número.
            return opcao;
        } catch (InputMismatchException e) {
            System.out.println("\nErro: Por favor, digite um número válido.");
            scanner.nextLine(); // Limpa o buffer do scanner para evitar loop infinito.
            return -1; // Retorna um valor inválido para repetir o menu.
        }
    }

    /**
     * Gerencia o processo de login do usuário.
     */
    private static void fazerLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Digite seu CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        // Pesquisa na lista de usuários se existe algum com o CPF informado.
        List<Usuario> usuarios = usuarioDAO.pesquisar(cpf);
        Usuario usuarioParaLogin = null;
        if (!usuarios.isEmpty()) {
            for(Usuario u : usuarios) {
                if(u.cpf().equals(cpf)) { // Garante que é o CPF exato
                    usuarioParaLogin = u;
                    break;
                }
            }
        }

        // Valida se o usuário foi encontrado e se a senha está correta.
        if (usuarioParaLogin != null && usuarioParaLogin.senha().equals(senha)) {
            usuarioLogado = usuarioParaLogin;
            System.out.println("\nLogin realizado com sucesso!");
        } else {
            System.out.println("\nCPF ou senha incorretos.");
        }
    }

    /**
     * Desconecta o usuário atual do sistema.
     */
    private static void fazerLogout() {
        usuarioLogado = null;
        System.out.println("\nVocê foi desconectado.");
    }

    /**
     * Gerencia o cadastro de um novo usuário, com tratamento de exceções.
     */
    private static void cadastrarNovoUsuario() {
        System.out.println("\n--- Cadastro de Novo Usuário ---");
        try {
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Nome completo: ");
            String nome = scanner.nextLine();
            System.out.print("E-mail: ");
            String email = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.print("Crie uma senha (mínimo 8 caracteres): ");
            String senha = scanner.nextLine();

            Usuario novoUsuario = new Usuario(cpf, nome, senha, email, telefone);
            usuarioDAO.inserir(novoUsuario);
        } catch (IllegalArgumentException e) {
            // Captura erros de validação da própria classe Usuario (ex: CPF em branco)
            System.out.println("\nErro ao cadastrar: " + e.getMessage());
        }
    }

    /**
     * Lista todos os pacotes de viagem disponíveis.
     */
    private static void listarPacotes() {
        System.out.println("\n--- Pacotes de Viagem Disponíveis ---");
        List<Pacote> pacotes = pacoteDAO.listarTodos();
        if (pacotes.isEmpty()) {
            System.out.println("Nenhum pacote disponível no momento.");
        } else {
            for (Pacote pacote : pacotes) {
                System.out.println("----------------------------------------");
                // O método toString() da classe Pacote é usado aqui para formatar a saída.
                System.out.println(pacote);
            }
        }
    }

    /**
     * Conduz o usuário pelo processo de criação de uma nova reserva.
     */
    private static void realizarReserva() {
        System.out.println("\n--- Realizar Reserva ---");
        listarPacotes();
        System.out.print("\nDigite o ID do pacote que deseja reservar: ");
        int idPacote = lerOpcao();

        if(idPacote == -1) return; // Se a entrada do usuário foi inválida, retorna.

        Pacote pacoteEscolhido = pacoteDAO.pesquisarPorId(idPacote);

        if (pacoteEscolhido == null) {
            System.out.println("\nPacote com ID " + idPacote + " não encontrado.");
            return;
        }

        // Tenta reservar uma vaga. O método retorna false se não houver vagas.
        if (pacoteEscolhido.reservarVaga()) {
            // Se a vaga foi reservada, atualiza o pacote no DAO para salvar a nova contagem de vagas.
            pacoteDAO.editar(pacoteEscolhido);

            // Cria o objeto Reserva e o insere no DAO.
            Reserva novaReserva = new Reserva(usuarioLogado, pacoteEscolhido, new Date(), new Date(), StatusReserva.CONFIRMADA);
            reservaDAO.inserir(novaReserva);
        } else {
            System.out.println("\nDesculpe, não há vagas disponíveis para este pacote.");
        }
    }

    /**
     * Consulta e exibe todas as reservas feitas pelo usuário logado.
     */
    private static void consultarMinhasReservas() {
        System.out.println("\n--- Minhas Reservas ---");
        List<Reserva> minhasReservas = reservaDAO.pesquisar(usuarioLogado.cpf());

        if (minhasReservas.isEmpty()) {
            System.out.println("Você ainda não possui nenhuma reserva.");
        } else {
            for (Reserva reserva : minhasReservas) {
                System.out.println("----------------------------------------");
                System.out.println(reserva);
            }
        }
    }

    /**
     * Popula o sistema com dados iniciais para teste e demonstração.
     * Este método é chamado apenas uma vez, no início da execução.
     */
    private static void carregarDadosIniciais() {
        try {
            // Criação de usuários de teste
            Usuario u1 = new Usuario("12345678900", "Germano Pinheiro", "senha1234", "germano@email.com", "5199998888");
            Usuario u2 = new Usuario("00987654321", "Rodrigo Cardoso", "senha5678", "rodrigo@email.com", "51977776666");
            usuarioDAO.inserir(u1);
            usuarioDAO.inserir(u2);

            // Criação de destinos
            Destino d1 = new Destino(1, "Serras Gaúchas", "Passeio pelas belas paisagens de Gramado e Canela.");
            Destino d2 = new Destino(2, "Praias de Santa Catarina", "Conheça as famosas praias de Florianópolis e Balneário Camboriú.");
            Destino d3 = new Destino(3, "Cataratas do Iguaçu", "Visite uma das sete maravilhas naturais do mundo.");
            destinoDAO.inserir(d1);
            destinoDAO.inserir(d2);
            destinoDAO.inserir(d3);

            // Criação de pacotes
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Pacote p1 = new Pacote(0, "Fim de Semana em Gramado", d1, 1200.00, sdf.parse("15/08/2025"), sdf.parse("17/08/2025"), "Inclui hotel e café da manhã.", 10);
            Pacote p2 = new Pacote(0, "Verão em Florianópolis", d2, 2500.00, sdf.parse("10/01/2026"), sdf.parse("17/01/2026"), "Inclui passagem aérea e hotel.", 5);
            Pacote p3 = new Pacote(0, "Aventura em Foz do Iguaçu", d3, 1800.00, sdf.parse("01/10/2025"), sdf.parse("05/10/2025"), "Inclui hotel e passeios.", 0); // Pacote sem vagas para teste

            pacoteDAO.inserir(p1);
            pacoteDAO.inserir(p2);
            pacoteDAO.inserir(p3);

            System.out.println(">>> Dados iniciais carregados com sucesso! <<<");

        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Erro ao carregar dados iniciais: " + e.getMessage());
        }
    }
}
