package control;

import modelo.Destino;
import modelo.DestinoDAO;
import java.util.List;
import java.util.Scanner;

/**
 * Gerencia a interação do usuário para as operações de CRUD de Destinos.
 */
public class GerenciadorDestino {
    private final DestinoDAO destinoDAO;
    private final Scanner scanner;

    /**
     * Construtor que recebe as dependências necessárias.
     * @param destinoDAO O objeto de acesso a dados para destinos.
     * @param scanner O objeto Scanner para ler a entrada do console.
     */
    public GerenciadorDestino(DestinoDAO destinoDAO, Scanner scanner) {
        this.destinoDAO = destinoDAO;
        this.scanner = scanner;
    }

    /**
     * Exibe o menu de gerenciamento de destinos e processa a escolha do usuário.
     */
    public void gerenciar() {
        int opcao;
        do {
            exibirSubMenu("Destinos");
            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> inserir();
                case 2 -> listar();
                case 3 -> pesquisar();
                case 4 -> editar();
                case 5 -> excluir();
                case 0 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void inserir() {
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
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao inserir o destino.");
        }
    }

    private void listar() {
        System.out.println("\n--- Lista de Destinos ---");
        List<Destino> lista = destinoDAO.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum destino cadastrado.");
        } else {
            for (Destino d : lista) {
                System.out.println(d);
                System.out.println("--------------------");
            }
        }
    }

    private void pesquisar() {
        System.out.println("\n--- Pesquisar Destino ---");
        System.out.print("Digite o termo para pesquisar no nome do destino: ");
        String termo = scanner.nextLine();
        List<Destino> resultados = destinoDAO.pesquisar(termo);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum destino encontrado com o termo '" + termo + "'.");
        } else {
            System.out.println("Destinos encontrados:");
            for (Destino d : resultados) {
                System.out.println(d);
                System.out.println("--------------------");
            }
        }
    }

    private void editar() {
        try {
            System.out.println("\n--- Editar Destino ---");
            System.out.print("Digite o ID do destino que deseja editar: ");
            int id = lerOpcao();

            // Verifica se o destino existe antes de pedir novos dados.
            if (destinoDAO.listarTodos().stream().noneMatch(d -> d.idDestino() == id)) {
                System.out.println("Erro: Destino com ID " + id + " não encontrado.");
                return;
            }

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

    private void excluir() {
        try {
            System.out.println("\n--- Excluir Destino ---");
            System.out.print("Digite o ID do destino que deseja excluir: ");
            int id = lerOpcao();

            // Para o método excluir do DAO, só precisamos de um objeto com o ID correto.
            Destino destinoParaExcluir = new Destino(id, "nome temporario", "descricao temporaria");
            destinoDAO.excluir(destinoParaExcluir);

        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao excluir o destino.");
        }
    }

    private void exibirSubMenu(String entidade) {
        System.out.println("\n--- Gerenciar " + entidade + " ---");
        System.out.println("1. Inserir");
        System.out.println("2. Listar Todos");
        System.out.println("3. Pesquisar");
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
}