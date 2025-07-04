package view;

import modelo.Destino;
import modelo.DestinoDAO;
import modelo.Pacote;
import modelo.PacoteDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Gerencia a interação do usuário para as operações de CRUD de Pacotes.
 */
public class GerenciadorPacote {
    private final PacoteDAO pacoteDAO;
    private final DestinoDAO destinoDAO; // Dependência adicional para associar pacotes a destinos
    private final Scanner scanner;

    public GerenciadorPacote(PacoteDAO pacoteDAO, DestinoDAO destinoDAO, Scanner scanner) {
        this.pacoteDAO = pacoteDAO;
        this.destinoDAO = destinoDAO;
        this.scanner = scanner;
    }

    /**
     * Exibe o menu de gerenciamento de pacotes e processa a escolha do usuário.
     */
    public void gerenciar() {
        int opcao;
        do {
            exibirSubMenu("Pacotes");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> inserir();
                case 2 -> listar();
                case 3 -> pesquisarPorDestino();
                case 4 -> editar();
                case 5 -> excluir();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void inserir() {
        System.out.println("\n--- Inserir Novo Pacote ---");
        try {
            System.out.print("Digite o nome do pacote: ");
            String nome = scanner.nextLine();

            // Exibe os destinos disponíveis para o usuário escolher
            System.out.println("\nDestinos disponíveis:");
            if (destinoDAO.listarTodos().isEmpty()) {
                System.out.println("Nenhum destino cadastrado. Por favor, cadastre um destino primeiro.");
                return;
            }
            listarEntidades(destinoDAO.listarTodos());

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

            // O ID é gerado automaticamente pelo DAO, então passamos 0
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

    private void listar() {
        System.out.println("\n--- Lista de Pacotes ---");
        List<Pacote> lista = pacoteDAO.listarTodos();
        listarEntidades(lista);
    }

    private void pesquisarPorDestino() {
        System.out.println("\n--- Pesquisar Pacotes por Destino ---");
        System.out.print("Digite o nome do destino para buscar pacotes: ");
        String nomeDestino = scanner.nextLine();

        List<Pacote> resultados = pacoteDAO.pesquisar(nomeDestino);
        System.out.println("Pacotes encontrados para '" + nomeDestino + "':");
        listarEntidades(resultados);
    }

    private void editar() {
        System.out.println("\n--- Editar Pacote ---");
        try {
            System.out.print("Digite o ID do pacote que deseja editar: ");
            int idPacote = lerOpcao();

            // Para editar, precisamos primeiro buscar o objeto original.
            // Seria ideal ter um método pesquisarPorId no DAO.
            Pacote pacoteOriginal = pacoteDAO.listarTodos().stream()
                    .filter(p -> p.getId() == idPacote)
                    .findFirst()
                    .orElse(null);

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

    private void excluir() {
        System.out.println("\n--- Excluir Pacote ---");
        try {
            System.out.print("Digite o ID do pacote a ser excluído: ");
            int id = lerOpcao();

            Pacote pacoteParaExcluir = new Pacote(id, "dummy", new Destino(0, "dummy", "dummy"), 1, new Date(), new Date(), "", 0);
            pacoteDAO.excluir(pacoteParaExcluir);

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao excluir o pacote.");
        }
    }

    private void exibirSubMenu(String entidade) {
        System.out.println("\n--- Gerenciar " + entidade + " ---");
        System.out.println("1. Inserir");
        System.out.println("2. Listar Todos");
        System.out.println("3. Pesquisar por Destino");
        System.out.println("4. Editar");
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