package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservaDAO implements OperacoesDAO<Reserva> {

    private static final List<Reserva> repositorioDeReservas = new ArrayList<>();
    private static int proximoId = 1;

    @Override
    public void inserir(Reserva reserva) {
        reserva.setIdReserva(proximoId++); // Atribui um ID único e incrementa
        repositorioDeReservas.add(reserva);
        System.out.println("Reserva ID " + reserva.getIdReserva() + " para o usuário " + reserva.getUsuario().nome() + " inserida com sucesso!");
    }

    @Override
    public void excluir(Reserva reserva) {
        if (repositorioDeReservas.remove(reserva)) {
            System.out.println("Reserva ID " + reserva.getIdReserva() + " removida com sucesso!");
        } else {
            System.out.println("Reserva não encontrada para exclusão.");
        }
    }

    @Override
    public void editar(Reserva reservaAtualizada) {
        int index = -1;
        for (int i = 0; i < repositorioDeReservas.size(); i++) {
            if (repositorioDeReservas.get(i).getIdReserva() == reservaAtualizada.getIdReserva()) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            repositorioDeReservas.set(index, reservaAtualizada);
            System.out.println("Reserva ID " + reservaAtualizada.getIdReserva() + " editada com sucesso!");
        } else {
            System.out.println("Reserva não encontrada para edição.");
        }
    }

    /**
     * Pesquisa reservas pelo CPF do usuário.
     * @param cpf O CPF do usuário para buscar as reservas.
     * @return Uma lista de reservas associadas ao CPF.
     */
    @Override
    public List<Reserva> pesquisar(String cpf) {
        return repositorioDeReservas.stream()
                .filter(r -> r.getUsuario().cpf().equals(cpf))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reserva> listarTodos() {
        return new ArrayList<>(repositorioDeReservas);
    }
}