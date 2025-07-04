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
                    gerenciarDestinos(); // <-- Funcionalidade agora implementada
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

    // --- MÉTODOS DE EXIBIÇÃO DE MENU (sem alterações) ---

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
                case 1:
                    inserirUsuario();
                    break;
                case 2:
                    listarTodos(usuarioDAO, "Usuários");
                    break;
                // Implementar os outros casos...
            }
        } while (opcao != 0);
    }

    /**
     * NOVO MÉTODO: Controla o fluxo de gerenciamento de destinos.
     */
    private static void gerenciarDestinos() {
        int opcao;
        do {
            exibirSubMenu("Destinos");
            opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    inserirDestino();
                    break;
                case 2:
                    listarTodos(destinoDAO, "Destinos");
                    break;
                case 3:
                    pesquisarDestino();
                    break;
                case 4:
                    editarDestino();
                    break;
                case 5:
                    excluirDestino();
                    break;
            }
        } while (opcao != 0);
    }

    private static void gerenciarPacotes() {
        int opcao;
        do {
            exibirSubMenu("Pacotes");
            opcao = lerOpcao();
            scanner.nextLine(); // Limpa o buffer do scanner

            switch (opcao) {
                case 1:
                    inserirPacote();
                    break;
                case 2:
                    listarTodos(pacoteDAO, "Pacotes");
                    break;
                case 3:
                    pesquisarPacotePorDestino();
                    break;
                case 4:
                    editarPacote();
                    break;
                case 5:
                    excluirPacote();
                    break;
            }
        } while (opcao != 0);
    }

    private static void gerenciarReservas() {
        int opcao;
        do {
            exibirSubMenu("Reservas");
            opcao = lerOpcao();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    inserirReserva();
                    break;
                case 2:
                    listarTodos(reservaDAO, "Reservas");
                    break;
                case 3:
                    pesquisarReservaPorCPF();
                    break;
                case 4:
                    editarStatusReserva();
                    break;
                case 5:
                    excluirReserva();
                    break;
            }
        } while (opcao != 0);
    }

    private static void inserirReserva() {
        System.out.println("\n--- Criar Nova Reserva ---");
        try {
            // Lista e seleciona o usuário
            System.out.println("Usuários disponíveis:");
            listarTodos(usuarioDAO, "Usuários");
            System.out.print("Digite o CPF do usuário para a reserva: ");
            String cpfUsuario = scanner.nextLine();
            Usuario usuarioSelecionado = usuarioDAO.pesquisar(cpfUsuario).stream().findFirst().orElse(null);

            if (usuarioSelecionado == null) {
                System.out.println("Erro: Usuário com CPF " + cpfUsuario + " não encontrado.");
                return;
            }

            // Lista e seleciona o pacote
            System.out.println("\nPacotes disponíveis:");
            listarTodos(pacoteDAO, "Pacotes");
            System.out.print("Digite o ID do pacote para a reserva: ");
            int idPacote = lerOpcao();
            scanner.nextLine();

            Pacote pacoteSelecionado = pacoteDAO.listarTodos().stream().filter(p -> p.getId() == idPacote).findFirst().orElse(null);

            if (pacoteSelecionado == null) {
                System.out.println("Erro: Pacote com ID " + idPacote + " não encontrado.");
                return;
            }

            // Tenta reservar a vaga e lança exceção se não houver disponibilidade
            pacoteSelecionado.reservarVaga();
            pacoteDAO.editar(pacoteSelecionado); // Salva o novo número de vagas

            Date dataReserva = new Date(); // Data atual
            Date dataLimite = new Date(dataReserva.getTime() + (7 * 24 * 60 * 60 * 1000)); // 7 dias a partir de hoje

            Reserva novaReserva = new Reserva(usuarioSelecionado, pacoteSelecionado, dataReserva, dataLimite, StatusReserva.PENDENTE);
            reservaDAO.inserir(novaReserva);

        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Erro ao criar reserva: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    private static void pesquisarReservaPorCPF() {
        System.out.println("\n--- Pesquisar Reservas por CPF ---");
        System.out.print("Digite o CPF do usuário: ");
        String cpf = scanner.nextLine();

        List<Reserva> resultados = reservaDAO.pesquisar(cpf);
        System.out.println("Reservas encontradas para o CPF " + cpf + ":");
        listar(resultados);
    }

    private static void editarStatusReserva() {
        System.out.println("\n--- Alterar Status da Reserva ---");
        try {
            System.out.print("Digite o ID da reserva que deseja alterar: ");
            int idReserva = lerOpcao();
            scanner.nextLine();

            Reserva reserva = reservaDAO.listarTodos().stream().filter(r -> r.getIdReserva() == idReserva).findFirst().orElse(null);

            if (reserva == null) {
                System.out.println("Erro: Reserva com ID " + idReserva + " não encontrada.");
                return;
            }

            System.out.println("Status atual: " + reserva.getStatus());
            System.out.println("Escolha o novo status: 1. Confirmar | 2. Cancelar");
            int opcaoStatus = lerOpcao();
            scanner.nextLine();

            switch (opcaoStatus) {
                case 1:
                    reserva.confirmar();
                    break;
                case 2:
                    reserva.cancelar();
                    // Lógica para devolver a vaga ao pacote
                    Pacote pacote = reserva.getPacote();
                    pacote.setVagasDisponiveis(pacote.getVagasDisponiveis() + 1);
                    pacoteDAO.editar(pacote);
                    System.out.println("Uma vaga foi devolvida ao pacote '" + pacote.getNome() + "'.");
                    break;
                default:
                    System.out.println("Opção de status inválida.");
                    return;
            }
            // Salva a reserva com o status atualizado
            reservaDAO.editar(reserva);

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao alterar o status: " + e.getMessage());
        }
    }

    private static void excluirReserva() {
        System.out.println("\n--- Excluir Reserva ---");
        try {
            System.out.print("Digite o ID da reserva a ser excluída: ");
            int id = lerOpcao();
            scanner.nextLine();

            Reserva reservaParaExcluir = new Reserva(null, null, null, null, null);
            reservaParaExcluir.setIdReserva(id);
            reservaDAO.excluir(reservaParaExcluir);

        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao excluir a reserva.");
        }
    }

    // --- MÉTODOS DE OPERAÇÕES PARA DESTINOS (NOVOS) ---

    private static void inserirDestino() {
        try {
            System.out.println("\n--- Inserir Novo Destino ---");
            System.out.print("Digite o ID do Destino: ");
            int id = lerOpcao(); // Reutiliza o leitor de opção para ler um inteiro
            scanner.nextLine(); // Consome a quebra de linha deixada pelo nextInt

            System.out.print("Digite o Nome do País/Destino: ");
            String nome = scanner.nextLine();

            System.out.print("Digite a Descrição: ");
            String descricao = scanner.nextLine();

            Destino novoDestino = new Destino(id, nome, descricao);
            destinoDAO.inserir(novoDestino);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro nos dados fornecidos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao inserir o destino.");
        }
    }

    private static void pesquisarDestino() {
        System.out.println("\n--- Pesquisar Destino ---");
        System.out.print("Digite o termo para pesquisar no nome do destino: ");
        String termo = scanner.nextLine();
        List<Destino> resultados = destinoDAO.pesquisar(termo);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum destino encontrado com o termo '" + termo + "'.");
        } else {
            System.out.println("Destinos encontrados:");
            listar(resultados);
        }
    }

    private static void editarDestino() {
        try {
            System.out.println("\n--- Editar Destino ---");
            System.out.print("Digite o ID do destino que deseja editar: ");
            int id = lerOpcao();
            scanner.nextLine(); // Limpa o buffer

            System.out.print("Digite o novo Nome do País/Destino: ");
            String novoNome = scanner.nextLine();

            System.out.print("Digite a nova Descrição: ");
            String novaDescricao = scanner.nextLine();

            Destino destinoAtualizado = new Destino(id, novoNome, novaDescricao);
            destinoDAO.editar(destinoAtualizado);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro nos dados fornecidos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao editar o destino.");
        }
    }

    private static void excluirDestino() {
        try {
            System.out.println("\n--- Excluir Destino ---");
            System.out.print("Digite o ID do destino que deseja excluir: ");
            int id = lerOpcao();
            scanner.nextLine(); // Limpa o buffer

            // Para excluir, precisamos criar um objeto temporário com o mesmo ID
            // A descrição e o nome podem ser vazios, pois equals/hashCode do record usam todos os campos
            // ou se sobrescritos, usam o ID. Para o DAO com HashMap, apenas o ID importa na exclusão.
            Destino destinoParaExcluir = new Destino(id, "dummy", "dummy");
            destinoDAO.excluir(destinoParaExcluir);

        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao excluir o destino.");
        }
    }


    // --- MÉTODOS DE OPERAÇÕES (EXISTENTES E ATUALIZADOS) ---

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

            Usuario novoUsuario;
            if (telefone.isBlank()) {
                novoUsuario = new Usuario(cpf, nome, senha, email);
            } else {
                novoUsuario = new Usuario(cpf, nome, senha, email, telefone);
            }
            usuarioDAO.inserir(novoUsuario);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar usuário: " + e.getMessage());
        }
    }

    /**
     * Método genérico para listar todos os registros de qualquer DAO.
     */
    private static void listarTodos(OperacoesDAO<?> dao, String nomeEntidade) {
        System.out.println("\n--- Lista de " + nomeEntidade + " ---");
        List<?> lista = dao.listarTodos();
        listar(lista);
    }

    /**
     * Método auxiliar para imprimir qualquer lista.
     */
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

    // --- MÉTODOS DE OPERAÇÕES PARA PACOTES (NOVOS) ---

    private static void inserirPacote() {
        System.out.println("\n--- Inserir Novo Pacote ---");
        try {
            System.out.print("Digite o nome do pacote: ");
            String nome = scanner.nextLine();

            // Exibe os destinos disponíveis para o usuário escolher
            System.out.println("Destinos disponíveis:");
            listarTodos(destinoDAO, "Destinos");
            System.out.print("Digite o ID do destino para este pacote: ");
            int idDestino = lerOpcao();
            scanner.nextLine(); // Limpa o buffer

            // Busca o objeto Destino pelo ID
            // Nota: seria ideal ter um método `pesquisarPorId` no DAO.
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

            // ID é gerado pelo DAO, então passamos 0
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

    private static void pesquisarPacotePorDestino() {
        System.out.println("\n--- Pesquisar Pacotes por Destino ---");
        System.out.print("Digite o nome do destino para buscar pacotes: ");
        String nomeDestino = scanner.nextLine();

        List<Pacote> resultados = pacoteDAO.pesquisar(nomeDestino);
        System.out.println("Pacotes encontrados para '" + nomeDestino + "':");
        listar(resultados);
    }

    private static void editarPacote() {
        System.out.println("\n--- Editar Pacote ---");
        System.out.print("Digite o ID do pacote que deseja editar: ");
        int idPacote = lerOpcao();
        scanner.nextLine(); // Limpa o buffer

        // Verifica se o pacote existe antes de pedir novos dados
        if (!pacoteDAO.listarTodos().stream().anyMatch(p -> p.getId() == idPacote)) {
            System.out.println("Erro: Pacote com ID " + idPacote + " não encontrado.");
            return;
        }

        // Se existe, pede os novos dados (lógica similar ao inserirPacote)
        System.out.println("Digite os novos dados para o pacote ID " + idPacote + " (deixe em branco para não alterar):");
        // ... (aqui entraria a lógica completa para pedir cada campo e atualizar)
        System.out.println("Funcionalidade de edição completa ainda não implementada.");
    }

    private static void excluirPacote() {
        System.out.println("\n--- Excluir Pacote ---");
        System.out.print("Digite o ID do pacote a ser excluído: ");
        int id = lerOpcao();
        scanner.nextLine(); // Limpa o buffer

        // Para excluir, o DAO com HashMap só precisa do ID.
        // Criamos um objeto temporário apenas para passar o ID.
        Pacote pacoteParaExcluir = new Pacote(id, "dummy", new Destino(0, "dummy", "dummy"), 1, new Date(), new Date(), "", 0);
        pacoteDAO.excluir(pacoteParaExcluir);
    }

    private static void preCarregarDados() {
        // Adiciona dados iniciais para facilitar os testes
        usuarioDAO.inserir(new Usuario("111.111.111-11", "Ana Clara", "senha123", "ana@email.com"));
        destinoDAO.inserir(new Destino(1, "Serra Gaúcha", "Passeio em Gramado e Canela."));
        pacoteDAO.inserir(new Pacote(0, "Inverno na Serra", new Destino(1, "Serra Gaúcha", ""), 2500.00, new Date(), new Date(), "Roteiro...", 10));
    }

    private static int lerOpcao() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}