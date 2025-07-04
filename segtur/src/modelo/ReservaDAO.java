package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservaDAO implements OperacoesDAO<Reserva> {
    private static final Map<Integer, Reserva> repositorioDeReservas = new HashMap<>();
    private static int proximoId = 1;

    @Override
    public void inserir(Reserva reserva) {
        int id = proximoId++;
        reserva.setIdReserva(id);
        repositorioDeReservas.put(id, reserva);
        System.out.println("Reserva ID " + id + " para o usuário '" + reserva.getUsuario().nome() + "' inserida com sucesso!");
    }

    @Override
    public void excluir(Reserva reserva) {
        Reserva removida = repositorioDeReservas.remove(reserva.getIdReserva());
        if (removida != null) {
            System.out.println("Reserva ID " + removida.getIdReserva() + " removida com sucesso!");
        } else {
            System.out.println("Reserva com ID " + reserva.getIdReserva() + " não encontrada para exclusão.");
        }
    }

    @Override
    public void editar(Reserva reservaAtualizada) {
        if (repositorioDeReservas.containsKey(reservaAtualizada.getIdReserva())) {
            repositorioDeReservas.put(reservaAtualizada.getIdReserva(), reservaAtualizada);
            System.out.println("Reserva ID " + reservaAtualizada.getIdReserva() + " editada com sucesso!");
        } else {
            System.out.println("Reserva com ID " + reservaAtualizada.getIdReserva() + " não encontrada para edição.");
        }
    }

    @Override
    public List<Reserva> pesquisar(String cpf) {
        return repositorioDeReservas.values().stream()
                .filter(r -> r.getUsuario().cpf().equals(cpf))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserva> listarTodos() {
        return new ArrayList<>(repositorioDeReservas.values());
    }
}