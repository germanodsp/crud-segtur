package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe de Objeto de Acesso a Dados (DAO) para a entidade Pacote.
 * Responsável por simular a persistência de dados em memória, utilizando
 * uma lista estática (ArrayList), conforme os requisitos do projeto.
 */
public class PacoteDAO implements OperacoesDAO<Pacote> {

    private static List<Pacote> listaDePacotes = new ArrayList<>();

    private static int proximoId = 1;

    @Override
    public void inserir(Pacote pacote) {
        // Define um ID único para o novo pacote antes de salvá-lo
        pacote.setId(proximoId++);
        listaDePacotes.add(pacote);
    }

    @Override
    public void excluir(Pacote pacote) {
        // O método remove() usa o método equals() da classe Pacote para encontrar o objeto.
        // É fundamental que a classe Pacote tenha um equals() e hashCode() implementado corretamente (baseado no ID).
        listaDePacotes.remove(pacote);
    }

    @Override
    public void editar(Pacote pacoteAtualizado) {
        for (int i = 0; i < listaDePacotes.size(); i++) {
            if (listaDePacotes.get(i).getId() == pacoteAtualizado.getId()) {
                listaDePacotes.set(i, pacoteAtualizado);
                return;
            }
        }
    }

    @Override
    public List<Pacote> pesquisar(String nomeDestino) {
        return listaDePacotes.stream()
                .filter(p -> p.getDestino().nome().toLowerCase().contains(nomeDestino.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pacote> listarTodos() {
        return new ArrayList<>(listaDePacotes);
    }

    public Pacote pesquisarPorId(int id) {
        for (Pacote p : listaDePacotes) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null; // Retorna null se não encontrar
    }
}