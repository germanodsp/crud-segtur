package control;

import modelo.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GerenciadorReserva {
    private final ReservaDAO reservaDAO;
    private final UsuarioDAO usuarioDAO;
    private final PacoteDAO pacoteDAO;
    private final Scanner scanner;
    private final Usuario usuarioLogado;



    public GerenciadorReserva(ReservaDAO reservaDAO, UsuarioDAO usuarioDAO, PacoteDAO pacoteDAO, Scanner scanner, Usuario usuarioLogado) {
        this.reservaDAO = reservaDAO;
        this.usuarioDAO = usuarioDAO;
        this.pacoteDAO = pacoteDAO;
        this.scanner = scanner;
        this.usuarioLogado = usuarioLogado;
    }


    public void gerenciar() {
        int opcao;
        do {
            exibirSubMenu("Reservas");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> inserir();
                case 2 -> listar();
                case 3 -> pesquisarPorCPF();
                case 4 -> editarStatus();
                case 5 -> excluir();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void inserir() {
        System.out.println("\n--- Criar Nova Reserva para " + usuarioLogado.nome() + " ---");
        try {
            // << MELHORIA: Não precisa mais perguntar quem é o usuário >>

            // 1. Seleciona o pacote
            System.out.println("\nPacotes disponíveis:");
            listarEntidades(pacoteDAO.listarTodos());
            System.out.print("Digite o ID do pacote para a reserva: ");
            int idPacote = lerOpcao();

            Pacote pacoteSelecionado = pacoteDAO.listarTodos().stream()
                    .filter(p -> p.getId() == idPacote)
                    .findFirst()
                    .orElse(null);

            if (pacoteSelecionado == null) {
                System.out.println("Erro: Pacote com ID " + idPacote + " não encontrado.");
                return;
            }


            pacoteSelecionado.reservarVaga();
            pacoteDAO.editar(pacoteSelecionado);


            Date dataReserva = new Date();
            Date dataLimite = new Date(dataReserva.getTime() + (7 * 24 * 60 * 60 * 1000));

            Reserva novaReserva = new Reserva(usuarioLogado, pacoteSelecionado, dataReserva, dataLimite, StatusReserva.PENDENTE);
            reservaDAO.inserir(novaReserva);

        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Erro ao criar reserva: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n--- Lista de Reservas ---");
        List<Reserva> lista = reservaDAO.listarTodos();
        listarEntidades(lista);
    }

    private void pesquisarPorCPF() {
        System.out.println("\n--- Pesquisar Reservas por CPF ---");
        System.out.print("Digite o CPF do usuário: ");
        String cpf = scanner.nextLine();

        List<Reserva> resultados = reservaDAO.pesquisar(cpf);
        System.out.println("Reservas encontradas para o CPF " + cpf + ":");
        listarEntidades(resultados);
    }

    private void editarStatus() {
        System.out.println("\n--- Alterar Status da Reserva ---");
        try {
            System.out.print("Digite o ID da reserva que deseja alterar: ");
            int idReserva = lerOpcao();

            Reserva reserva = reservaDAO.listarTodos().stream()
                    .filter(r -> r.getIdReserva() == idReserva)
                    .findFirst()
                    .orElse(null);

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

    private void excluir() {
        System.out.println("\n--- Excluir Reserva ---");
        try {
            System.out.print("Digite o ID da reserva a ser excluída: ");
            int id = lerOpcao();

            Reserva reservaParaExcluir = new Reserva(new Usuario("0","","",""), new Pacote(0,"",null,0,new Date(),new Date(),"",0), new Date(), new Date(), StatusReserva.PENDENTE);
            reservaParaExcluir.setIdReserva(id);
            reservaDAO.excluir(reservaParaExcluir);

        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao excluir a reserva.");
        }
    }

    private void exibirSubMenu(String entidade) {
        System.out.println("\n--- Gerenciar " + entidade + " ---");
        System.out.println("1. Inserir");
        System.out.println("2. Listar Todos");
        System.out.println("3. Pesquisar por CPF");
        System.out.println("4. Alterar Status (Confirmar/Cancelar)");
        System.out.println("5. Excluir");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número válido.");
            return -1;
        }
    }

    private void listarEntidades(List<?> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
        } else {
            for (Object obj : lista) {
                System.out.println(obj.toString());
                System.out.println("--------------------");
            }
        }
    }
}