package view;

import modelo.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Classe principal para testar as operações da classe ReservaDAO.
 */
public class MainReserva {

    public static void main(String[] args) throws InterruptedException {
        // --- PREPARAÇÃO: Instanciando DAOs e criando dados de base ---
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        DestinoDAO destinoDAO = new DestinoDAO();
        PacoteDAO pacoteDAO = new PacoteDAO();
        ReservaDAO reservaDAO = new ReservaDAO();

        // Criando usuários e destinos
        Usuario usuario1 = new Usuario("111.111.111-11", "Ana Clara", "senha123", "ana@email.com");
        usuarioDAO.inserir(usuario1);

        Destino destino1 = new Destino(1, "Serra Gaúcha", "Passeio em Gramado e Canela.");
        destinoDAO.inserir(destino1);

        // Criando um pacote
        Pacote pacote1 = new Pacote(0, "Inverno na Serra", destino1, 2500.00, new Date(), new Date(), 10);
        pacoteDAO.inserir(pacote1);

        System.out.println("--- INSERINDO NOVAS RESERVAS ---");
        // Criando as reservas
        Date hoje = new Date();
        Date dataLimite = new Date(hoje.getTime() + TimeUnit.DAYS.toMillis(7)); // Limite de 7 dias

        // A vaga do pacote é decrementada antes de criar a reserva
        pacote1.reservarVaga();
        pacoteDAO.editar(pacote1); // Salva o estado atualizado do pacote

        Reserva r1 = new Reserva(usuario1, pacote1, hoje, dataLimite, StatusReserva.PENDENTE);
        reservaDAO.inserir(r1);

        // Criando uma segunda reserva para o mesmo usuário
        pacote1.reservarVaga();
        pacoteDAO.editar(pacote1);
        Reserva r2 = new Reserva(usuario1, pacote1, hoje, dataLimite, StatusReserva.PENDENTE);
        reservaDAO.inserir(r2);


        System.out.println("\n--- LISTANDO TODAS AS RESERVAS ---");
        List<Reserva> listaDeReservas = reservaDAO.listarTodos();
        for (Reserva r : listaDeReservas) {
            System.out.println(r);
            System.out.println("--------------------");
        }

        System.out.println("\n--- CONFIRMANDO UMA RESERVA ---");
        // Pega a reserva com ID 1 e a confirma
        Reserva reservaParaConfirmar = r1;
        reservaParaConfirmar.confirmar(); // Altera o status do objeto para CONFIRMADA
        reservaDAO.editar(reservaParaConfirmar); // Persiste a alteração no DAO

        System.out.println("\n--- PESQUISANDO RESERVAS PELO CPF DO USUÁRIO ---");
        List<Reserva> pesquisa = reservaDAO.pesquisar("111.111.111-11");
        for (Reserva r : pesquisa) {
            System.out.println("Reserva encontrada para Ana Clara (ID: " + r.getIdReserva() + "), Status: " + r.getStatus());
        }


        System.out.println("\n--- CANCELANDO UMA RESERVA ---");
        Reserva reservaParaCancelar = r2;
        reservaParaCancelar.cancelar();
        reservaDAO.editar(reservaParaCancelar);

        System.out.println("\n--- LISTA FINAL DE RESERVAS ---");
        listaDeReservas = reservaDAO.listarTodos();
        for (Reserva r : listaDeReservas) {
            System.out.println(r);
            System.out.println("--------------------");
        }
    }
}