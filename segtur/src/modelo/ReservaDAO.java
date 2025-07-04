package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe de Objeto de Acesso a Dados (DAO) para a entidade Reserva.
 * Utiliza um HashMap para um armazenamento otimizado por ID da reserva,
 * permitindo operações de busca, edição e exclusão de alta performance.
 */
public class ReservaDAO implements OperacoesDAO<Reserva> {

    // HashMap para acesso direto e eficiente às reservas pela chave (ID).
    private static final Map<Integer, Reserva> repositorioDeReservas = new HashMap<>();
    private static int proximoId = 1;

    /**
     * Insere uma nova reserva no repositório.
     * Um ID único é gerado e atribuído à reserva.
     * @param reserva O objeto Reserva a ser inserido.
     */
    @Override
    public void inserir(Reserva reserva) {
        int id = proximoId++;
        reserva.setIdReserva(id);
        repositorioDeReservas.put(id, reserva);
        System.out.println("Reserva ID " + id + " para o usuário '" + reserva.getUsuario().nome() + "' inserida com sucesso!");
    }

    /**
     * Exclui uma reserva do repositório com base em seu ID.
     * @param reserva O objeto Reserva a ser excluído.
     */
    @Override
    public void excluir(Reserva reserva) {
        // A remoção pela chave no HashMap é uma operação de tempo constante (muito rápida).
        Reserva removida = repositorioDeReservas.remove(reserva.getIdReserva());
        if (removida != null) {
            System.out.println("Reserva ID " + removida.getIdReserva() + " removida com sucesso!");
        } else {
            System.out.println("Reserva com ID " + reserva.getIdReserva() + " não encontrada para exclusão.");
        }
    }

    /**
     * Edita uma reserva existente.
     * A operação substitui a reserva antiga pela nova se o ID já existir no mapa.
     * @param reservaAtualizada O objeto Reserva com os dados atualizados.
     */
    @Override
    public void editar(Reserva reservaAtualizada) {
        if (repositorioDeReservas.containsKey(reservaAtualizada.getIdReserva())) {
            repositorioDeReservas.put(reservaAtualizada.getIdReserva(), reservaAtualizada);
            System.out.println("Reserva ID " + reservaAtualizada.getIdReserva() + " editada com sucesso!");
        } else {
            System.out.println("Reserva com ID " + reservaAtualizada.getIdReserva() + " não encontrada para edição.");
        }
    }

    /**
     * Pesquisa todas as reservas associadas a um CPF de usuário.
     * @param cpf O CPF do usuário para a busca.
     * @return Uma lista de reservas do usuário especificado.
     */
    @Override
    public List<Reserva> pesquisar(String cpf) {
        // Como a pesquisa não é pela chave do mapa (ID), percorremos os valores.
        return repositorioDeReservas.values().stream()
                .filter(r -> r.getUsuario().cpf().equals(cpf))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserva> listarTodos() {
        return new ArrayList<>(repositorioDeReservas.values());
    }
}