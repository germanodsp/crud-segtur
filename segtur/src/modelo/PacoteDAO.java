package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PacoteDAO implements OperacoesDAO<Pacote> {

    private static final Map<Integer, Pacote> repositorioDePacotes = new HashMap<>();
    private static int proximoId = 1;

    @Override
    public void inserir(Pacote pacote) {
        int id = proximoId++;
        pacote.setId(id);
        repositorioDePacotes.put(id, pacote);
        System.out.println("Pacote '" + pacote.getNome() + "' inserido com o ID: " + id);
    }

    @Override
    public void excluir(Pacote pacote) {
        Pacote removido = repositorioDePacotes.remove(pacote.getId());
        if (removido != null) {
            System.out.println("Pacote '" + removido.getNome() + "' removido com sucesso!");
        } else {
            System.out.println("Pacote com ID " + pacote.getId() + " não encontrado para exclusão.");
        }
    }

    @Override
    public void editar(Pacote pacoteAtualizado) {
        if (repositorioDePacotes.containsKey(pacoteAtualizado.getId())) {
            repositorioDePacotes.put(pacoteAtualizado.getId(), pacoteAtualizado);
            System.out.println("Pacote '" + pacoteAtualizado.getNome() + "' editado com sucesso!");
        } else {
            System.out.println("Pacote com ID " + pacoteAtualizado.getId() + " não encontrado para edição.");
        }
    }

    @Override
    public List<Pacote> pesquisar(String nomeDestino) {
        return repositorioDePacotes.values().stream()
                .filter(p -> p.getDestino().nome().toLowerCase().contains(nomeDestino.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pacote> listarTodos() {
        return new ArrayList<>(repositorioDePacotes.values());
    }

    public Pacote pesquisarPorId(int id) {
        return repositorioDePacotes.get(id);
    }

}