package testes;


import modelo.Destino;
import modelo.DestinoDAO;
import modelo.Pacote;
import modelo.PacoteDAO;

import java.util.Date;
import java.util.List;

/**
 * Classe principal para testar as operações da classe PacoteDAO.
 */
public class MainPacote {

    public static void main(String[] args) {
        // --- PREPARAÇÃO: Instanciando DAOs e criando Destinos ---
        DestinoDAO destinoDAO = new DestinoDAO();
        PacoteDAO pacoteDAO = new PacoteDAO();

        // Criando alguns destinos para associar aos pacotes
        Destino destinoBrasil = new Destino(1, "Brasil", "Carnavel e praias lindas");
        Destino destinoPortugal = new Destino(2, "Portugal", "País europeu com rica história.");
        destinoDAO.inserir(destinoBrasil);
        destinoDAO.inserir(destinoPortugal);

        // --- INÍCIO DOS TESTES DO PACOTE DAO ---

        System.out.println("--- INSERINDO NOVOS PACOTES ---");
        // Criando pacotes usando os dois construtores da classe Pacote
        Pacote p1 = new Pacote(0, "Carnaval no Rio", destinoBrasil, 5500.00, new Date(), new Date(), "Visita ao Cristo, Pão de Açúcar e desfiles.", 30);
        Pacote p2 = new Pacote(0, "Férias em Lisboa", destinoPortugal, 7800.00, new Date(), new Date(), 20); // Usando o construtor adicional

        pacoteDAO.inserir(p1);
        pacoteDAO.inserir(p2);

        System.out.println("\n--- LISTANDO TODOS OS PACOTES ---");
        List<Pacote> listaDePacotes = pacoteDAO.listarTodos();
        for (Pacote p : listaDePacotes) {
            System.out.println(p);
            System.out.println("--------------------");
        }

        System.out.println("\n--- TESTANDO A RESERVA DE VAGAS ---");
        try {
            // Pega o pacote 1 (Carnaval no Rio) que foi inserido
            Pacote pacoteParaReservar = p1;
            System.out.println("Vagas antes da reserva: " + pacoteParaReservar.getVagasDisponiveis());
            pacoteParaReservar.reservarVaga(); // Tenta reservar uma vaga
            System.out.println("Vagas após a reserva: " + pacoteParaReservar.getVagasDisponiveis());
            // Atualiza o pacote no repositório com a vaga reservada
            pacoteDAO.editar(pacoteParaReservar);
        } catch (IllegalStateException e) {
            // Este bloco captura a exceção se não houver mais vagas
            System.out.println("Erro ao reservar vaga: " + e.getMessage());
        }

        System.out.println("\n--- EDITANDO UM PACOTE ---");
        // Cria um novo objeto com o mesmo ID (2) para atualizar o pacote de Lisboa
        Pacote pacoteEditado = new Pacote(2, "Férias de Verão em Lisboa e Porto", destinoPortugal, 8200.00, new Date(), new Date(), "Roteiro completo pelas duas maiores cidades de Portugal.", 15);
        pacoteDAO.editar(pacoteEditado);

        System.out.println("\n--- PESQUISANDO PACOTES NO 'Brasil' ---");
        List<Pacote> pesquisa = pacoteDAO.pesquisar("Brasil");
        for (Pacote p : pesquisa) {
            System.out.println("Resultado da pesquisa: " + p.getNome());
        }

        System.out.println("\n--- EXCLUINDO UM PACOTE ---");
        pacoteDAO.excluir(p1);

        System.out.println("\n--- LISTA FINAL DE PACOTES ---");
        listaDePacotes = pacoteDAO.listarTodos();
        if (listaDePacotes.isEmpty()) {
            System.out.println("Nenhum pacote restante no repositório.");
        } else {
            for (Pacote p : listaDePacotes) {
                System.out.println(p);
                System.out.println("--------------------");
            }
        }
    }
}