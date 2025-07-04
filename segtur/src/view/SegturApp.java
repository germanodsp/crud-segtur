package view;

import modelo.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal que executa a aplicação de console da SEGTUR.
 * Orquestra as operações de CRUD para todas as entidades do sistema.
 */
public class SegturApp {

    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final DestinoDAO destinoDAO = new DestinoDAO();
    private static final PacoteDAO pacoteDAO = new PacoteDAO();
    private static final ReservaDAO reservaDAO = new ReservaDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        preCarregarDados();
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    gerenciarUsuarios();
                    break;
                case 2:
                    gerenciarDestinos();
                    break;
                case 3:
                    gerenciarPacotes();
                    break;
                case 4:
                    gerenciarReservas();
                    break;
                case 0:
                    System.out.println("Obrigado por utilizar o sistema SEGTUR. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    // --- MÉTODOS DE EXIBIÇÃO DE MENU ---

    private static void exibirMenuPrincipal() {
        System.out.println("\n===== SEGTUR TURISMO - MENU PRINCIPAL =====");
        System.out.println("1. Gerenciar Usuários");
        System.out.println("2. Gerenciar Destinos");
        System.out.println("3. Gerenciar Pacotes");
        System.out.println("4. Gerenciar Reservas");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirSubMenu(String entidade) {
        System.out.println("\n--- Gerenciar " + entidade + " ---");
        System.out.println("1. Inserir");
        System.out.println("2. Listar Todos");
        System.out.println("3. Pesquisar");
        System.out.println("4. Editar");
        System.out.println("5. Excluir");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
    }

    // --- MÉTODOS GERENCIADORES ---

    private static void gerenciarUsuarios() {
        int opcao;
        do {
            exibirSubMenu("Usuários");
            opcao = lerOpcao();
            switch (opcao) {
                case 1 -> inserirUsuario();
                case 2 -> listarTodos(usuarioDAO, "Usuários");
                case 3 -> pesquisarUsuario();
                case 4 -> System.out.println("Funcionalidade de edição de usuário não implementada.");
                case 5 -> excluirUsuario();
            }
        } while (opcao != 0);
    }

    private static void gerenciarDestinos() {
        int opcao;
        do {
            exibirSubMenu("Destinos");
            opcao = lerOpcao();
            switch (opcao) {
                case 1 -> inserirDestino();
                case 2 -> listarTodos(destinoDAO, "Destinos");
                case 3 -> pesquisarDestino();
                case 4 -> editarDestino();
                case 5 -> excluirDestino();
            }
        } while (opcao != 0);
    }

    private static void gerenciarPacotes() {
        int opcao;
        do {
            exibirSubMenu("Pacotes");
            opcao = lerOpcao();
            switch (opcao) {
                case 1 -> inserirPacote();
                case 2 -> listarTodos(pacoteDAO, "Pacotes");
                case 3 -> pesquisarPacotePorDestino();
                case 4 -> editarPacote();
                case 5 -> excluirPacote();
            }
        } while (opcao != 0);
    }

    private static void gerenciarReservas() {
        int opcao;
        do {
            exibirSubMenu("Reservas");
            opcao = lerOpcao();
            switch (opcao) {
                case 1 -> inserirReserva();
                case 2 -> listarTodos(reservaDAO, "Reservas");
                case 3 -> pesquisarReservaPorCPF();
                case 4 -> editarStatusReserva();
                case 5 -> excluirReserva();
            }
        } while (opcao != 0);
    }

    // --- MÉTODOS DE OPERAÇÕES PARA USUÁRIOS ---

    private static void inserirUsuario() {
        try {
            System.out.println("\n--- Inserir Novo Usuário ---");
            System.out.print("Digite o CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Digite o Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Digite a Senha (mínimo 8 caracteres): ");
            String senha = scanner.nextLine();
            System.out.print("Digite o Email: ");
            String email = scanner.nextLine();
            System.out.print("Digite o Telefone (opcional): ");
            String telefone = scanner.nextLine();

            Usuario novoUsuario = new Usuario(cpf, nome, senha, email, telefone);
            usuarioDAO.inserir(novoUsuario);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar usuário: " + e.getMessage());
        }
    }

    private static void pesquisarUsuario() {
        System.out.println("\n--- Pesquisar Usuário por Nome ---");
        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();
        List<Usuario> resultados = usuarioDAO.pesquisar(nome);
        listar(resultados);
    }

    private static void excluirUsuario() {
        System.out.println("\n--- Excluir Usuário ---");
        System.out.print("Digite o CPF do usuário a ser excluído: ");
        String cpf = scanner.nextLine();
        Usuario usuarioParaExcluir = new Usuario(cpf, "dummy", "senha12345", "dummy@email.com");
        usuarioDAO.excluir(usuarioParaExcluir);
    }


    // --- MÉTODOS DE OPERAÇÕES PARA DESTINOS ---

    private static void inserirDestino() {
        try {
            System.out.println("\n--- Inserir Novo Destino ---");
            System.out.print("Digite o ID do Destino: ");
            int id = lerOpcao();

            System.out.print("Digite o Nome do País/Destino: ");
            String nome = scanner.nextLine();

            System.out.print("Digite a Descrição: ");
            String descricao = scanner.nextLine();

            Destino novoDestino = new Destino(id, nome, descricao);
            destinoDAO.inserir(novoDestino);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro nos dados fornecidos: " + e.getMessage());
        }
    }

    private static void pesquisarDestino() {
        System.out.println("\n--- Pesquisar Destino ---");
        System.out.print("Digite o termo para pesquisar no nome do destino: ");
        String termo = scanner.nextLine();
        List<Destino> resultados = destinoDAO.pesquisar(termo);
        listar(resultados);
    }

    private static void editarDestino() {
        try {
            System.out.println("\n--- Editar Destino ---");
            System.out.print("Digite o ID do destino que deseja editar: ");
            int id = lerOpcao();

            System.out.print("Digite o novo Nome do País/Destino: ");
            String novoNome = scanner.nextLine();

            System.out.print("Digite a nova Descrição: ");
            String novaDescricao = scanner.nextLine();

            Destino destinoAtualizado = new Destino(id, novoNome, novaDescricao);
            destinoDAO.editar(destinoAtualizado);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro nos dados fornecidos: " + e.getMessage());
        }
    }

    private static void excluirDestino() {
        System.out.println("\n--- Excluir Destino ---");
        System.out.print("Digite o ID do destino que deseja excluir: ");
        int id = lerOpcao();
        Destino destinoParaExcluir = new Destino(id, "dummy");
        destinoDAO.excluir(destinoParaExcluir);
    }

    // --- MÉTODOS DE OPERAÇÕES PARA PACOTES ---

    private static void inserirPacote() {
        System.out.println("\n--- Inserir Novo Pacote ---");
        try {
            System.out.print("Digite o nome do pacote: ");
            String nome = scanner.nextLine();

            System.out.println("Destinos disponíveis:");
            listarTodos(destinoDAO, "Destinos");
            System.out.print("Digite o ID do destino para este pacote: ");
            int idDestino = lerOpcao();

            Destino destinoSelecionado = destinoDAO.listarTodos().stream()
                    .filter(d -> d.idDestino() == idDestino)
                    .findFirst()
                    .orElse(null);

            if (destinoSelecionado == null) {
                System.out.println("Erro: Destino com ID " + idDestino + " não encontrado.");
                return;
            }

            System.out.print("Digite o preço do pacote (ex: 2500.50): ");
            double preco = Double.parseDouble(scanner.nextLine());

            System.out.print("Digite a data de início (dd/MM/yyyy): ");
            Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());

            System.out.print("Digite a data de fim (dd/MM/yyyy): ");
            Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());

            System.out.print("Digite o itinerário: ");
            String itinerario = scanner.nextLine();

            System.out.print("Digite o número de vagas disponíveis: ");
            int vagas = Integer.parseInt(scanner.nextLine());

            Pacote novoPacote = new Pacote(0, nome, destinoSelecionado, preco, dataInicio, dataFim, itinerario, vagas);
            pacoteDAO.inserir(novoPacote);

        } catch (ParseException e) {
            System.out.println("Erro: Formato de data inválido. Use dd/MM/yyyy.");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Formato de número inválido para preço ou vagas.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro nos dados fornecidos: " + e.getMessage());
        }
    }

    private static void editarPacote() {
        System.out.println("\n--- Editar Pacote ---");
        try {
            System.out.print("Digite o ID do pacote que deseja editar: ");
            int idPacote = lerOpcao();

            Pacote pacoteOriginal = pacoteDAO.pesquisarPorId(idPacote);
            if (pacoteOriginal == null) {
                System.out.println("Erro: Pacote com ID " + idPacote + " não encontrado.");
                return;
            }

            System.out.println("Digite os novos dados (deixe em branco para manter o valor atual):");

            System.out.print("Novo nome (" + pacoteOriginal.getNome() + "): ");
            String nome = scanner.nextLine();
            if (nome.isBlank()) nome = pacoteOriginal.getNome();

            System.out.print("Novo preço (" + pacoteOriginal.getPreco() + "): ");
            String precoStr = scanner.nextLine();
            double preco = precoStr.isBlank() ? pacoteOriginal.getPreco() : Double.parseDouble(precoStr);

            System.out.print("Novas vagas (" + pacoteOriginal.getVagasDisponiveis() + "): ");
            String vagasStr = scanner.nextLine();
            int vagas = vagasStr.isBlank() ? pacoteOriginal.getVagasDisponiveis() : Integer.parseInt(vagasStr);

            Pacote pacoteAtualizado = new Pacote(idPacote, nome, pacoteOriginal.getDestino(), preco, pacoteOriginal.getDataInicio(), pacoteOriginal.getDataFim(), pacoteOriginal.getItinerario(), vagas);
            pacoteDAO.editar(pacoteAtualizado);

        } catch (NumberFormatException e) {
            System.out.println("Erro: Formato de número inválido.");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    private static void pesquisarPacotePorDestino() {
        System.out.println("\n--- Pesquisar Pacotes por Destino ---");
        System.out.print("Digite o nome do destino para buscar pacotes: ");
        String nomeDestino = scanner.nextLine();
        List<Pacote> resultados = pacoteDAO.pesquisar(nomeDestino);
        listar(resultados);
    }

    private static void excluirPacote() {
        System.out.println("\n--- Excluir Pacote ---");
        System.out.print("Digite o ID do pacote a ser excluído: ");
        int id = lerOpcao();
        Pacote pacoteParaExcluir = pacoteDAO.pesquisarPorId(id);
        if (pacoteParaExcluir != null) {
            pacoteDAO.excluir(pacoteParaExcluir);
        } else {
            System.out.println("Pacote não encontrado.");
        }
    }


    // --- MÉTODOS DE OPERAÇÕES PARA RESERVAS ---

    private static void inserirReserva() {
        System.out.println("\n--- Criar Nova Reserva ---");
        try {
            System.out.println("Usuários disponíveis:");
            listarTodos(usuarioDAO, "Usuários");
            System.out.print("Digite o nome do usuário para a reserva: ");
            String cpfUsuario = scanner.nextLine();
            Usuario usuarioSelecionado = usuarioDAO.pesquisar(cpfUsuario).stream().findFirst().orElse(null);

            if (usuarioSelecionado == null) {
                System.out.println("Erro: Usuário com CPF " + cpfUsuario + " não encontrado.");
                return;
            }

            System.out.println("\nPacotes disponíveis:");
            listarTodos(pacoteDAO, "Pacotes");
            System.out.print("Digite o ID do pacote para a reserva: ");
            int idPacote = lerOpcao();

            Pacote pacoteSelecionado = pacoteDAO.pesquisarPorId(idPacote);

            if (pacoteSelecionado == null) {
                System.out.println("Erro: Pacote com ID " + idPacote + " não encontrado.");
                return;
            }

            pacoteSelecionado.reservarVaga();
            pacoteDAO.editar(pacoteSelecionado);

            Date dataReserva = new Date();
            Date dataLimite = new Date(dataReserva.getTime() + (7 * 24 * 60 * 60 * 1000));

            Reserva novaReserva = new Reserva(usuarioSelecionado, pacoteSelecionado, dataReserva, dataLimite, StatusReserva.PENDENTE);
            reservaDAO.inserir(novaReserva);

        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Erro ao criar reserva: " + e.getMessage());
        }
    }

    private static void pesquisarReservaPorCPF() {
        System.out.println("\n--- Pesquisar Reservas por CPF ---");
        System.out.print("Digite o CPF do usuário: ");
        String cpf = scanner.nextLine();
        List<Reserva> resultados = reservaDAO.pesquisar(cpf);
        listar(resultados);
    }

    private static void editarStatusReserva() {
        System.out.println("\n--- Alterar Status da Reserva ---");
        try {
            System.out.print("Digite o ID da reserva que deseja alterar: ");
            int idReserva = lerOpcao();

            Reserva reserva = reservaDAO.listarTodos().stream().filter(r -> r.getIdReserva() == idReserva).findFirst().orElse(null);

            if (reserva == null) {
                System.out.println("Erro: Reserva com ID " + idReserva + " não encontrada.");
                return;
            }

            System.out.println("Status atual: " + reserva.getStatus());
            System.out.println("Escolha o novo status: 1. Confirmar | 2. Cancelar");
            int opcaoStatus = lerOpcao();

            switch (opcaoStatus) {
                case 1 -> reserva.confirmar();
                case 2 -> {
                    reserva.cancelar();
                    Pacote pacote = reserva.getPacote();
                    pacote.setVagasDisponiveis(pacote.getVagasDisponiveis() + 1);
                    pacoteDAO.editar(pacote);
                    System.out.println("Uma vaga foi devolvida ao pacote '" + pacote.getNome() + "'.");
                }
                default -> {
                    System.out.println("Opção de status inválida.");
                    return;
                }
            }
            reservaDAO.editar(reserva);

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao alterar o status: " + e.getMessage());
        }
    }

    private static void excluirReserva() {
        System.out.println("\n--- Excluir Reserva ---");
        System.out.print("Digite o ID da reserva a ser excluída: ");
        int id = lerOpcao();
        Reserva reservaParaExcluir = reservaDAO.listarTodos().stream().filter(r -> r.getIdReserva() == id).findFirst().orElse(null);
        if (reservaParaExcluir != null) {
            reservaDAO.excluir(reservaParaExcluir);
        } else {
            System.out.println("Reserva não encontrada.");
        }
    }

    // --- MÉTODOS UTILITÁRIOS ---

    private static void listarTodos(OperacoesDAO<?> dao, String nomeEntidade) {
        System.out.println("\n--- Lista de " + nomeEntidade + " ---");
        List<?> lista = dao.listarTodos();
        listar(lista);
    }

    private static void listar(List<?> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
        } else {
            for (Object obj : lista) {
                System.out.println(obj.toString());
                System.out.println("--------------------");
            }
        }
    }

    private static int lerOpcao() {
        try {
            // Sempre lê a linha inteira e depois converte para número.
            // Isso evita o problema do "enter" pendente no buffer.
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número válido.");
            return -1; // Retorna um valor inválido para indicar erro
        }
    }

    private static void preCarregarDados() {
        try {
            usuarioDAO.inserir(new Usuario("11122233344", "Ana Clara", "senha12345", "ana@email.com"));
            usuarioDAO.inserir(new Usuario("55566677788", "Bruno Costa", "senha67890", "bruno@email.com"));

            Destino d1 = new Destino(1, "Serra Gaúcha", "Passeio em Gramado e Canela.");
            Destino d2 = new Destino(2, "Rio de Janeiro", "Visite o Cristo Redentor e o Pão de Açúcar.");
            destinoDAO.inserir(d1);
            destinoDAO.inserir(d2);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            pacoteDAO.inserir(new Pacote(0, "Inverno na Serra", d1, 2500.00, sdf.parse("10/07/2025"), sdf.parse("15/07/2025"), "Hotel 4 estrelas e passeios inclusos.", 10));
            pacoteDAO.inserir(new Pacote(0, "Carnaval no Rio", d2, 4500.00, sdf.parse("28/02/2026"), sdf.parse("05/03/2026"), "Hospedagem próxima à Sapucaí.", 0));
        } catch(ParseException e) {
            System.out.println("Erro ao carregar dados iniciais (formato de data inválido).");
        }
    }
}
