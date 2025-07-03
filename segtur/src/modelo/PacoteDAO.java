package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe de Objeto de Acesso a Dados (DAO) para a entidade Pacote.
 * Responsável por simular a persistência de dados em memória, utilizando
 * uma lista estática (ArrayList), conforme os requisitos do projeto.
 */
public abstract class PacoteDAO implements OperacoesDAO<Pacote> {

    // Lista estática que funcionará como nosso "banco de dados" em memória.
    // Sendo estática, ela é única para toda a aplicação.
    private static List<Pacote> listaDePacotes = new ArrayList<>();

    // Variável para gerar IDs únicos para cada novo pacote.
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
            Pacote pacoteNaLista = listaDePacotes.get(i);

            // Encontra o pacote na lista pelo ID
            if (pacoteNaLista.getId() == pacoteAtualizado.getId()) {
                // Substitui o
            }
        }
    }
}