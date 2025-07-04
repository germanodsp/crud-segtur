package control;

import modelo.Usuario;
import modelo.UsuarioDAO;
import java.util.List;
import java.util.Scanner;

/**
 * Gerencia a interação do usuário para as operações de CRUD de Usuários.
 */
public class GerenciadorUsuario {
    private final UsuarioDAO usuarioDAO;
    private final Scanner scanner;

    public GerenciadorUsuario(UsuarioDAO usuarioDAO, Scanner scanner) {
        this.usuarioDAO = usuarioDAO;
        this.scanner = scanner;
    }

    public void gerenciar() {
        int opcao;
        do {
            exibirSubMenu("Usuários");
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

    private void listar() {
        System.out.println("\n--- Lista de Usuários ---");
        List<Usuario> lista = usuarioDAO.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            for (Usuario u : lista) {
                System.out.println(u);
                System.out.println("--------------------");
            }
        }
    }

    private void pesquisar() {
        System.out.println("\n--- Pesquisar Usuário por Nome ---");
        System.out.print("Digite o nome (ou parte do nome) do usuário: ");
        String nome = scanner.nextLine();
        List<Usuario> resultados = usuarioDAO.pesquisar(nome);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum usuário encontrado com o termo '" + nome + "'.");
        } else {
            System.out.println("Usuários encontrados:");
            for (Usuario u : resultados) {
                System.out.println(u);
                System.out.println("--------------------");
            }
        }
    }

    private void editar() {
        System.out.println("\n--- Editar Usuário ---");
        try {
            System.out.print("Digite o CPF do usuário que deseja editar: ");
            String cpf = scanner.nextLine();

            // A validação do CPF acontece aqui ao tentar buscar o objeto
            if (usuarioDAO.listarTodos().stream().noneMatch(u -> u.cpf().equals(cpf))) {
                System.out.println("Erro: Usuário com CPF " + cpf + " não encontrado.");
                return;
            }
            Usuario usuarioOriginal = usuarioDAO.listarTodos().stream().filter(u -> u.cpf().equals(cpf)).findFirst().get();

            System.out.println("Digite os novos dados (deixe em branco para manter o valor atual):");

            System.out.print("Novo nome (" + usuarioOriginal.nome() + "): ");
            String nome = scanner.nextLine();
            if (nome.isBlank()) nome = usuarioOriginal.nome();

            System.out.print("Nova senha: ");
            String senha = scanner.nextLine();
            if (senha.isBlank()) senha = usuarioOriginal.senha();

            System.out.print("Novo email (" + usuarioOriginal.email() + "): ");
            String email = scanner.nextLine();
            if (email.isBlank()) email = usuarioOriginal.email();

            System.out.print("Novo telefone (" + usuarioOriginal.telefone() + "): ");
            String telefone = scanner.nextLine();
            if (telefone.isBlank()) telefone = usuarioOriginal.telefone();

            Usuario usuarioAtualizado = new Usuario(cpf, nome, senha, email, telefone);
            usuarioDAO.editar(usuarioAtualizado);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro nos dados fornecidos: " + e.getMessage());
        }
    }

    private void excluir() {
        System.out.println("\n--- Excluir Usuário ---");
        try {
            System.out.print("Digite o CPF do usuário a ser excluído: ");
            String cpf = scanner.nextLine();

            // A exceção será lançada aqui se o CPF for inválido
            Usuario usuarioParaExcluir = new Usuario(cpf, "dummy", "senha12345", "dummy@email.com");
            usuarioDAO.excluir(usuarioParaExcluir);

        } catch (IllegalArgumentException e) {
            // Captura o erro de CPF inválido e informa o usuário
            System.out.println("Erro: " + e.getMessage());
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